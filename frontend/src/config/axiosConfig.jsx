import axios from 'axios';

/**
 * 요청을 보내는 baseURL 설정
 */
const client = axios.create({
  baseURL: 'http://localhost:3000/',
});

/**
 * request를 보낼때 localStorage에 token 정보가 있다면 헤더에 토큰 정보를 저장하고
 * 없다면 null로 처리
 */
client.interceptors.request.use(function (config) {
  // const user = localStorage.getItem('user');
  // if (!user) {
  //   config.headers['accessToken'] = null;
  //   config.headers['refreshToken'] = null;
  //   return config;
  // }
  // const { accessToken, refreshToken } = JSON.parse(user);
  config.headers['Authorization'] = `Bearer ${localStorage.getItem(
    'accessToken',
  )}`;
  // config.headers['refreshToken'] = localStorage.getItem('refreshToken');
  return config;
});

/**
 * response를 받았을 때, error가 발생했고 해당 error의 status가 401이라면
 * 기존의 originalRequest를 auth/refreshToken으로 전달해 토큰을 재발급 받습니다.
 * 여기서 401 이외의 오류가 들어온다면 토큰 재발급에 실패한 것으로 처리를 합니다.
 * 재발급 받은 토큰은 다시 로컬스토리지에 저장을 하고 헤더 부분에서 토큰 정보를 변경하고
 * 다시 originalRequest를 보냅니다.
 */
client.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (error) {
    if (error.response && error.response.status === 401) {
      try {
        const originalRequest = error.config;
        const response = await client.post('/api/auth/refresh', {
          accessToken: localStorage.getItem('accessToken'),
          refreshToken: localStorage.getItem('refreshToken'),
        });
        if (response) {
          // console.log(response);
          const { accessToken, refreshToken } = response.data.data;
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('refreshToken', refreshToken);
          originalRequest.headers[
            'Authorization'
          ] = `Bearer ${localStorage.getItem('accessToken')}`;
          return await client.request(originalRequest);
        }
      } catch (error) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        console.log(error);
      }
      return Promise.reject(error);
    }
    return Promise.reject(error);
  },
);

export default client;
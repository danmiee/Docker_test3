import axios from "axios";
import { React, useState } from "react";
import { useNavigate } from "react-router";
import jwt_decode from "jwt-decode";
import client from "../../config/axiosConfig";

const LogInInput = () => {
  const [loginId, setloginId] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();



  const clickLogin = async () => {
    
    if (loginId && password){
      const result = await axios.post('/api/auth/signIn',{
        loginId : loginId,
        password : password
      })
      .then((result)=>{
        console.log(result)
        const ACCESS_TOKEN = result.data.data.accessToken
        const REFRESH_TOKEN = result.data.data.refreshToken
        const decoded = jwt_decode(ACCESS_TOKEN);
        
        // 토큰 localStorage에 저장
        localStorage.setItem("accessToken", ACCESS_TOKEN)
        localStorage.setItem('refreshToken', REFRESH_TOKEN)
        // console.log(JSON.stringify(decoded))
      })
      // //SessionStorage에 저장
      .then((result)=>{
        client
        .get('api/member/me')
        .then((result)=>{
          sessionStorage.setItem('nickname',result.data.data.nickname)
          alert(`${sessionStorage.getItem('nickname')}님 반갑습니다`)
        
          result.data.code === 200 && navigate('/coinChart')
        })
      })
      
      .catch((err) => {
        alert(
          "아이디 또는 비밀번호를 잘못 입력했습니다.", 
        );
        console.log(err)
      });
      } else if (!loginId) {
      alert("아이디를 입력해주세요");
      return;
      } else {
      alert("비밀번호를 입력해주세요");
      return;
      }
};

  return (
    <>
      <div className="relative flex flex-col w-full transform border-b-2 bg-transparent text-lg duration-300 focus-within:border-lime-500">
        <input
          type="text"
          id="email"
          autoFocus
          placeholder="아이디 입력하세요"
          className="w-full border-none bg-transparent outline-none placeholder:italic focus:outline-none"
          onChange={(e) => {
            setloginId(e.target.value);
          }}
        />
      </div>
      <div className="relative flex flex-col w-full transform border-b-2 bg-transparent text-lg duration-300 focus-within:border-lime-500">
        <input
          type="password"
          id="password"
          placeholder="비밀번호 입력하세요"
          className="w-full border-none bg-transparent outline-none placeholder:italic focus:outline-none"
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
      </div>
      <button
        type="button"
        className="bg-indigo-500 w-full rounded hover:bg-indigo-600 hover:scale-105 py-2 mt-4 font-semibold"
        onClick={() => {
          clickLogin();
        }}
      >
        로그인
      </button>
    </>
  );
};

export default LogInInput;

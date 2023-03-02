import React from 'react'
import { useNavigate } from 'react-router'

const Home = () => {

  const navigate = useNavigate()

  return (
    <>
      <div className=''>
       <h2 className='flex items-center justify-center mt-32 text-3xl'>
        비트코인 실시간 시세예측 서비스
       </h2>
       <p className='flex flex-col items-center justify-center mt-24 text-[#BCC0C4]'>
        Predict Bit는 실시간 비트코인의 시세와 예측시세를 보여주며 여러분의 투자시 의사결정에 도움을 줍니다.
        <a className='flex border-2 rounded-full mt-11 w-72 h-12 items-center justify-center text-white 
        hover:scale-105 bg-purple-400 font-medium hover:bg-purple-500' href='/logIn'>
          <button 
          onClick={()=>{
            alert('로그인 후 사용가능 합니다')
            navigate('/logIn')
            }}><span>📈</span>시작</button>
          </a>      
        </p>
        
      </div>
      <div className='flex items-center justify-center mt-24'>
      <img src={process.env.PUBLIC_URL + '/img/example.gif'} alt="설명"
        className='w-max h-auto'/>

      </div>
    </>
  )
}

export default Home

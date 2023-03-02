import {React,useState} from "react";
import { useNavigate } from "react-router-dom";
import LogInInput from "./LogInInput";

const LogIn = () => {


const navigate = useNavigate()


  return (
    // 기존 로그인 코드에서 애니메이션 추가한 코드
    <div className="w-full min-h-screen flex justify-center items-center bg-gray-900">
        <div className="relative w-[400px] h-[480px] bg-gray-800 rounded-lg overflow-hidden">
            <div className="absolute w-[400px] h-[480px] bg-gradient-to-r from-indigo-500 via-indigo-500
            to-transparent -top-[50%] -left-[50%] animate-spin-slow origin-bottom-right"></div>
             <div className="absolute w-[400px] h-[480px] bg-gradient-to-r from-indigo-500 via-indigo-500
            to-transparent -top-[50%] -left-[50%] animate-spin-delay origin-bottom-right"></div>
            <div className="absolute inset-1 bg-gray-800 rounded-lg z-10 p-5">
                <form className="space-y-7">
                    <h2 className="text-2xl font-semibold text-indigo-500 text-center">로그인</h2>
                    <LogInInput/>
                      <button onClick={()=>{navigate('/signUp')}}
                     className="bg-red-400 w-full rounded hover:bg-red-500 hover:scale-105 py-2 font-semibold">
                      회원가입</button>
                </form>
            </div>
        </div>
      
    </div>
  );
};

export default LogIn;

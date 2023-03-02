import React, { useState } from "react";
import SignUpInput from "./SignUpInput";

const SignUp = () => {
  const [checkInputs, setCheckInputs] = useState([]);

  const changeHandler = (checked) => {
    if (checked) {
      alert("약관 동의 되었습니다");
    } else {
      setCheckInputs();
    }
  };

  return (
    <div className="w-full min-h-screen flex justify-center items-center bg-gray-900">
      <div className="relative w-[530px] h-[600px] bg-gray-800 rounded-lg overflow-hidden">
        <div
          className="absolute w-[530px] h-[600px] bg-gradient-to-r from-indigo-500 via-indigo-500
        to-transparent -top-[50%] -left-[50%] animate-spin-slow origin-bottom-right"
        ></div>
        <div
          className="absolute w-[530px] h-[600px] bg-gradient-to-r from-indigo-500 via-indigo-500
        to-transparent -top-[50%] -left-[50%] animate-spin-delay origin-bottom-right"
        ></div>
        <div className="absolute inset-1 bg-gray-800 rounded-lg z-10 p-5">
          <div className="space-y-7">
            <h2 className="text-2xl font-semibold text-indigo-500 text-center">
              회원가입
            </h2>
            <span className="flex space-x-2">
              <input
                type="checkbox"
                id="agree1"
                value="check1"
                onChange={(e) => {
                  changeHandler(e.currentTarget.checked);
                }}
              />
              <label> (필수) Predict Bit 회원 이용약관</label>
              <a
                onClick={(e) => {
                  e.preventDefault();
                  alert("준비 중입니다.");
                }}
                href="#"
                className="border border-1"
                title="새창 열림"
              >
                내용보기
              </a>
            </span>
            <span className="flex space-x-2">
              <input
                type="checkbox"
                id="agree2"
                value="check2"
                onChange={(e) => {
                  changeHandler(e.currentTarget.checked);
                }}
              />
              <label> (필수) Predict Bit 회원 개인정보 수집 및 이용동의</label>
              <a
                onClick={(e) => {
                  e.preventDefault();
                  alert("준비 중입니다.");
                }}
                href="#"
                className="border border-0.5"
                title="새창 열림"
              >
                내용보기
              </a>
            </span>
            <SignUpInput />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;

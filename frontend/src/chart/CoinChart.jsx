import React, { useRef, useEffect, useState } from "react";
import client from '../config/axiosConfig'
import MainChart from "./MainChart";
import CoinChart2 from "./CoinChart2";


const CoinChart = () => {


  useEffect(()=>{
    const timer = setInterval(()=>{
      client
      .get('/api/member/me')
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
    },1000000)
    return () => clearInterval(timer);
  }, []);



  return (
  <div className="grid lg:grid-rows-3 grid-flow-col bg-black ">
  <div className="col-span-4">
  <MainChart/>{/* 비트코인 실시간 차트 */}
  {/* <CoinChart2/>100개 데이터 차트 */}
  </div>
  </div>
  );
};

export default CoinChart;

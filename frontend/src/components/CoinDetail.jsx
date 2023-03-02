import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';


const CoinDetail = () => {
  const [data, setData] = useState([]);

  // 1초마다 코인 시세 현재가 정보 가져옴
  useEffect(() => {
    const intervalId = setInterval(async () => {
      const response = await axios.get('https://api.upbit.com/v1/ticker?markets=KRW-BTC');
      setData(response.data);
    }, 1000); 

    // clean up 함수
    return () => clearInterval(intervalId);
  }, []);
  
  // 가격변동 +, - 함수
  const changeLiteral = useCallback(change => {

    if (change === 'RISE') {
      return '+';
    } else if (change === 'FALL') {
      return '-';
    }
    return '';
  }, []);

  // 가격 단위 조정 함수
  const fixPrice = useCallback(price => {
    return parseInt(price.toFixed(0)).toLocaleString();
  }, []);

  return (
    <div className='row-span-2'>
      {data.map(item => (
        <div key={item.market} className="ml-2">
          <div className='space-y-1'>
          <h3 className='text-2xl font-bold'>비트코인</h3>
          {/* change가 rise면 빨강표시 아니면 fail 이면 파랑표시  */}
          <p className={`text-2xl ${item.change === 'RISE' ? "text-red-500" : "text-blue-500"}`}>
          {fixPrice(item.trade_price)}
          <span className='text-lg ml-1'>KRW</span></p>
          </div>
          <p className=''>전일대비
          <span
          className={`${item.change === 'RISE' ? "text-red-500" : "text-blue-500"}`}>
          {changeLiteral(item.change)}{(item.change_rate * 100).toFixed(2) }% 
          {changeLiteral(item.change)}{fixPrice(item.change_price)}
          <span className='text-xs ml-1'>KRW</span>
          </span>
          </p>
        </div>
      ))}
    </div>
  );
};

export default CoinDetail;

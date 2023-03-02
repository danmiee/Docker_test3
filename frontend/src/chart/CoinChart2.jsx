import React, { useRef, useEffect, useState } from "react";
import ApexCharts from "apexcharts";
import client from "../config/axiosConfig";

// 기존 차트 버젼
const CoinChart2 = () => {
  const chartRef = useRef(null);
  const [chartData, setChartData] = useState([]);
  const [minuteData, setMinuteData] = useState([]);
  const [loading, setLoading] = useState(true)

  useEffect(()=>{
    client
    .get('/api/historyCoins')
    .then((data)=>{
      const chartData = {
        name: "candle",
        type: "candlestick",
        data: data.data.data.list.map((item) => ({
          x: new Date(item.candle_date_time_kst),
          y: [item.opening_price, item.high_price, item.low_price, item.trade_price],
        })),
      };
      setMinuteData([chartData]); // 응답 데이터를 state에 저장
      setLoading(false); // 로딩 상태를 false로 변경
    })
    .catch((error)=>{
      console.log(error);
      setLoading(true); // 에러 발생 시 로딩 상태를 false로 변경
    })
  },[])

  useEffect(() => {
    // 차트 설정
    const options = {
      series: [
        {
          name: "candle",
          type: "candlestick",
          data: chartData,
        },
      ],
      chart: {
        height: 400,
        type: "candlestick",
      },
      xaxis: {
        type: "datetime",
        tickAmount: 10, // x축 눈금 개수
        labels: {
          datetimeUTC: false, // UTC 시간이 아닌 로컬 시간을 사용하도록 설정
          format: "HH:mm:ss", // x축 레이블 형식
        },
      },
      yaxis: {
        tooltip: {
          enabled: true,
        },
      },
    };

    const chart = new ApexCharts(chartRef.current, options);
    chart.render();

    return () => {
    };
  }, []);



  return (<>
  <div ref={chartRef} className="text-blue-400">
  </div>
  </>
  );
};

export default CoinChart2;
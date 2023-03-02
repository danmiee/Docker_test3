import React, { useRef, useEffect, useState } from "react";
import ApexCharts from "apexcharts";
import client from "../config/axiosConfig";

// 기존 차트 버젼
const CoinChart2 = () => {
  const chartRef = useRef(null);
  const [historyCoins, setHistoryCoins] = useState([]);
  const [test, setTest] = useState([''])
  let socket;

//   const updateObj = () => {
//     console.log('testCount', historyCoins);
// };


// useEffect(() => {
//     if (historyCoins.length === 0) return
//     updateObj()
//   }, [historyCoins]);



  const predict = () => {
    try {
      if (socket) {
        socket.close();
      }
      socket = new WebSocket("ws://localhost:8080/coin");
      socket.onopen = () => {
        console.log("WebSocket Open");
      };
      socket.onmessage = (msg) => {
        const data = JSON.parse(msg.data);
        const currentData = {
          x: new Date(data[0].candleDateTimeKst),
          y: [
            parseFloat(data[0].openingPrice),
            parseFloat(data[0].highPrice),
            parseFloat(data[0].lowPrice),
            parseFloat(data[0].tradePrice),
          ],
        };
        setHistoryCoins((prev) => [...prev, currentData]);
        console.log(historyCoins)
      };
    } catch (error) {
      console.log(error);
    }
  };

  const getHistoryCoins = async () => {
    try {
      const historyResponse = await client.get("/api/historyCoins");
      const historyData = historyResponse.data.data.list.map((item) => ({
        x: new Date(item.candleDateTimeKst),
        y: [item.openingPrice, item.highPrice, item.lowPrice, item.tradePrice],
      }));
      setHistoryCoins([...historyData, ...historyCoins]);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getHistoryCoins();
  }, []);

  useEffect(() => {
    // let xaxisType, xaxisFormat;
    // let tickAmount = 10;

    // // if (selectedInterval === "minutes/1") {
    // //   xaxisType = "datetime";
    // //   xaxisFormat = "HH:mm:ss";
    // //   tickAmount = 10;
    // // } else if (selectedInterval === "minutes/240") {
    // //   xaxisType = "datetime";
    // //   xaxisFormat = "MM/dd HH:mm:ss";
    // //   tickAmount = 24;
    // // } else if (selectedInterval === "days/") {
    // //   xaxisType = "datetime";
    // //   xaxisFormat = "MM/dd";
    // //   tickAmount = 10;
    // // } else {
    // //   xaxisType = "category";
    // //   xaxisFormat = undefined;
    // // }

    const options = {
      series: [
        {
          name: "시세",
          type: "candlestick",
          data: historyCoins,
        },
      ],
      chart: {
        height: 350,
        type: "candlestick",
      },
      annotations :{
        xaxis : [{
          x : new Date().getTime(),
          borderColor: '#00E396',
              label: {
                borderColor: '#00E396',
                style: {
                  fontSize: '20px',
                  color: '#fff',
                  background: '#00E396'
                },
                orientation: 'horizontal',
                offsetY: 5,
                text: '현재 시각'
              }
        }]
      },
      title: {
        text: "비트코인 차트",
        align: "Center",
      },
      stroke: {
        width: [3, 1],
      },
      xaxis: {
        type: "datetime",
        tickAmount: 50, // x축 눈금 개수
        labels: {
          datetimeUTC: false, // UTC 시간이 아닌 로컬 시간을 사용하도록 설정
          format: "yyyy-MM-dd HH:mm:ss", // format 바꾸기
          // format: "HH:mm:ss", // x축 레이블 형식
        },
      },
      tooltip: {
        shared: true,
      },
    };

    const chart = new ApexCharts(chartRef.current, options);
    chart.render();

    chart.updateSeries([
      {
        name: "시세",
        type: "candlestick",
        data: historyCoins,
      },
    ],);
    // chartRef.current.classList.add("minute");

    return () => {
      chart.destroy();
    };
  }, [historyCoins]);

  return (
    <>
    <div className="relative">
      <button
        className="absolute z-20 flex items-center right-0 mr-36
        bg-blue-900 hover:bg-blue-300 rounded border-spacing-2"
        onClick={() => predict()}
      >
        예측 시작
      </button>
      <div ref={chartRef} className="text-blue-400 z-10">
        
      </div>
    </div>
  </>
  );
};

export default CoinChart2;
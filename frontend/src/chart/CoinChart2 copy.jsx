import React, { useRef, useEffect } from "react";
import ApexCharts from "apexcharts";
import data from "../Data.json"

// 기존 차트 버젼
const CoinChart2 = () => {
  const chartRef = useRef(null);
  const CoinData = data

  useEffect(() => {
    const options = {
      series: [
        {
          name: "시세",
          type: "candlestick",
          data: 
            CoinData.data.map((item)=>({
              x : Date.parse(item.candle_date_time_kst),
              y : [item.opening_price, item.high_price, item.low_price, item.trade_price],   
            }))  
        },
        {
          name : "지표데이터",
          type : "line",
          data : [
            {
              x: new Date(0),
              y: 16760000
            }, {
              x: new Date(0),
              y: 17036000
            }, {
              x: new Date(0),
              y: 17170000
            }, {
              x: new Date(0),
              y: 17439000
            }
          ]
        }
      ],
      chart: {
        height: 350,
        type: "line",
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
        tickAmount: 10, // x축 눈금 개수
        labels: {
          datetimeUTC: false, // UTC 시간이 아닌 로컬 시간을 사용하도록 설정
          // format: "yyyy-MM-dd HH:mm:ss", // format 바꾸기
          format: "HH:mm:ss", // x축 레이블 형식
        },
      },
      tooltip: {
        shared: true,
       
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
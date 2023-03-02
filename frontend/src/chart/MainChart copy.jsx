import React, { useRef, useEffect, useState } from "react";
import ApexCharts from "apexcharts";
import { useInterval } from "../useInterval";
import CoinDetail from "../components/CoinDetail";
import getTodayDate from "../getTodayDate";


const MainChart = () => {
  const chartRef = useRef(null);
  const [chartData, setChartData] = useState([]);
  const [selectedInterval, setSelectedInterval] = useState("minutes/1");

  useInterval(() => {
    fetch(
      `https://api.upbit.com/v1/candles/${selectedInterval}?market=KRW-BTC&count=60`
    )
      .then((res) => res.json())
      .then((data) => {
        const newData = data.map((item) => ({
          x: new Date(item.candle_date_time_kst),
          y: [item.opening_price, item.high_price, item.low_price, item.trade_price],
        }));
        setChartData(newData);
      })
      .catch((err) => console.log(err));
  }, 5000);

  useEffect(() => {
    let xaxisType, xaxisFormat;
    let tickAmount = 10;

    if (selectedInterval === "minutes/1") {
      xaxisType = "datetime";
      xaxisFormat = "HH:mm:ss";
      tickAmount = 10
    } else if (selectedInterval === "minutes/240") {
      xaxisType = "datetime";
      xaxisFormat = "MM/dd HH:mm:ss";
      tickAmount = 24;
    } else if (selectedInterval === "days/") {
      xaxisType = "datetime";
      xaxisFormat = "MM/dd";
      tickAmount = 10;
    } else {
      xaxisType = "category";
      xaxisFormat = undefined;
    }

    const options = {
      series: [
        {
          name: "candle",
          type: "candlestick",
          data: chartData,
        },
      ],
      chart: {
        height: 500,
        type: "candlestick",
      },
      annotations :{
        xaxis : [{
          x : new Date().getMinutes(),
          borderColor: '#00E396',
              label: {
                borderColor: '#00E396',
                style: {
                  fontSize: '13px',
                  color: '#fff',
                  background: '#00E396'
                },
                orientation: 'horizontal',
                offsetY: 5,
                text: '현재 시각'
              }
        }]
      },
      xaxis: {
        type: xaxisType,
        tickAmount: tickAmount,
        labels: {
          datetimeUTC: false,
          format: xaxisFormat,

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
 // 차트 업데이트 될때마다 데이터 업데이트
  chart.updateSeries(selectedInterval)

  if (selectedInterval === "minutes/1") {
    chartRef.current.classList.add("minutes/1")
  } else if (selectedInterval === "minutes/240") {
    chartRef.current.classList.add("minutes/240")
  } else if (selectedInterval === "days/") {
    chartRef.current.classList.add("days/")
  } 

    return () => {
      chart.destroy();
    };
  }, [selectedInterval,chartData]);

  const handleIntervalChange = (interval) => {
    setSelectedInterval(interval);
  };
  return (
    <div className="chart-container">
      <div className="chart-header">
        <h2>Bitcoin Chart</h2>
        <div className="chart-interval">
          <button
            className={`${selectedInterval === "minutes/60" ? "active" : ""} btn-interval`}
            onClick={() => handleIntervalChange("minutes/60")}
          >
            1분
          </button>
          <button
            className={`${selectedInterval === "minutes/240" ? "active" : ""} btn-interval`}
            onClick={() => handleIntervalChange("minutes/240")}
          >
            4시간
          </button>
          <button
            className={`${selectedInterval === "days/" ? "active" : ""} btn-interval`}
            onClick={() => handleIntervalChange("days/")}
          >
            1일
          </button>
        </div>
      </div>
      <div className="text-blue-400" ref={chartRef}></div>
      <CoinDetail />
    </div>
  );
};

export default MainChart

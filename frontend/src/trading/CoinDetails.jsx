import styled from "styled-components";
import CoinInfo from "./CoinInfo";
import RealTimeChart from "./RealTimeChart";
import RealTimeOrderBook from "./RealTimeOrderBook";
import RealTimeTradeHistory from "./RealTimeTradeHistory";

const DetailLayout = styled.div`
  height: 800px;
  background-color: black;
  padding: 5px;
  display: grid;
  gap: 5px;
  grid-template-columns: 1fr 1fr 1.1fr;
  grid-template-rows: 105px 300px 1fr;
`;

function CoinDetails() {
  return (
    <DetailLayout>
      <CoinInfo />
      {/* <RealTimeChart/> */}
      <RealTimeTradeHistory />
      <RealTimeOrderBook />
    </DetailLayout>
  );
}

export default CoinDetails;

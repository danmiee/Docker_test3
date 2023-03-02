package com.backend.web.historyCoin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoryCoinDTO {
    @Data
    public static class Basic {
        private Long idx;
        private Double openingPrice;
        private Double highPrice;
        private Double lowPrice;
        private Double tradePrice;
        private Double candleAccTradeVolume;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime candleDateTimeKst;
    }

    @Data
    public static class CoinList {
        private List<Basic> list = new ArrayList<>();
    }
}

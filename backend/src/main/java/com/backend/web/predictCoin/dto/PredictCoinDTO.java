package com.backend.web.predictCoin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PredictCoinDTO {
    @Data
    public static class Basic {
        private Long idx;
        private Double price;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime dateTime;
    }

    @Data
    public static class CoinList {
        private List<Basic> list = new ArrayList<>();
    }
}

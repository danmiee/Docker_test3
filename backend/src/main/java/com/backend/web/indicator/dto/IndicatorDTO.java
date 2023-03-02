package com.backend.web.indicator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IndicatorDTO {
    @Data
    public static class Basic {
        private Long idx;
        private Double bbp;
        private Double bbc;
        private Double bbm;
        private Double ma;
        private Double macd;
        private Double rsi;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime dateTime;
    }

    @Data
    public static class IndicatorList {
        private List<Basic> list = new ArrayList<>();
    }
}

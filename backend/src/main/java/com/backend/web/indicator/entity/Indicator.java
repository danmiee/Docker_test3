package com.backend.web.indicator.entity;

import com.backend.web.indicator.dto.IndicatorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "indicator")
public class Indicator {
    @Id
    private Long idx;

    // BB 볼린저 밴드
    private Double bbp;
    private Double bbc;
    private Double bbm;

    // MA
    private Double ma;

    // MACD
    private Double macd;

    // RSI
    private Double rsi;

    @Column(name = "date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @Builder
    public Indicator(
            Long idx,
            Double bbp,
            Double bbc,
            Double bbm,
            Double ma,
            Double macd,
            Double rsi,
            LocalDateTime dateTime
    ) {
        this.idx = idx;
        this.bbp = bbp;
        this.bbc = bbc;
        this.bbm = bbm;
        this.ma = ma;
        this.macd = macd;
        this.rsi = rsi;
        this.dateTime = dateTime;
    }

    public IndicatorDTO.Basic toIndicatorBasicDTO() {
        return new ModelMapper().map(this, IndicatorDTO.Basic.class);
    }
}

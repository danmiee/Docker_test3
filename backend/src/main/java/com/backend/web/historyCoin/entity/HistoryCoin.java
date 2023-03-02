package com.backend.web.historyCoin.entity;

import com.backend.web.historyCoin.dto.HistoryCoinDTO;
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
@Table(name = "historycoin")
public class HistoryCoin {
    @Id
    private Long idx;

    @Column(name = "opening_price")
    private Double openingPrice;

    @Column(name = "high_price")
    private Double highPrice;

    @Column(name = "low_price")
    private Double lowPrice;

    @Column(name = "trade_price")
    private Double tradePrice;

    @Column(name = "candle_acc_trade_volume")
    private Double candleAccTradeVolume;

    @Column(name = "candle_date_time_kst")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime candleDateTimeKst;

    @Builder
    public HistoryCoin(
            Long idx,
            Double openingPrice,
            Double highPrice,
            Double lowPrice,
            Double tradePrice,
            Double candleAccTradeVolume,
            LocalDateTime candleDateTimeKst
            ) {
        this.idx = idx;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradePrice = tradePrice;
        this.candleAccTradeVolume = candleAccTradeVolume;
        this.candleDateTimeKst = candleDateTimeKst;
    }

    public HistoryCoinDTO.Basic toHistoryCoinBasicDTO(){
        return new ModelMapper().map(this, HistoryCoinDTO.Basic.class);
    }
}

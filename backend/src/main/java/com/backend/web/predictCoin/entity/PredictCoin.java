package com.backend.web.predictCoin.entity;

import com.backend.web.predictCoin.dto.PredictCoinDTO;
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
@Table(name = "predictcoin")
public class PredictCoin {
    @Id
    private Long idx;

    private Double price;

    @Column(name = "date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @Builder
    public PredictCoin(
            Long idx,
            Double price,
            LocalDateTime dateTime
    ) {
        this.idx = idx;
        this.price = price;
        this.dateTime = dateTime;
    }

    public PredictCoinDTO.Basic toPredictCoinBasicDTO() {
        return new ModelMapper().map(this, PredictCoinDTO.Basic.class);
    }
}

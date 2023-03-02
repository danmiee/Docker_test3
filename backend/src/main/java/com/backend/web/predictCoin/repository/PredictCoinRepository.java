package com.backend.web.predictCoin.repository;

import com.backend.web.predictCoin.entity.PredictCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictCoinRepository extends JpaRepository<PredictCoin, Long> {
}

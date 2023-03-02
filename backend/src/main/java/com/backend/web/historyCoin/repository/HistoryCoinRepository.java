package com.backend.web.historyCoin.repository;

import com.backend.web.historyCoin.entity.HistoryCoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryCoinRepository extends JpaRepository<HistoryCoin, Long> {

    List<HistoryCoin> findByIdxBetween(long startIdx, long endIdx);
}
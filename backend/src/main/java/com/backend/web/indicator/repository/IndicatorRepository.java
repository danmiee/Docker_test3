package com.backend.web.indicator.repository;

import com.backend.web.indicator.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {

    List<Indicator> findByIdxBetween(long startIdx, long endIdx);
}

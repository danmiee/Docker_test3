package com.backend.web.indicator.service;

import com.backend.common.model.CustomException;
import com.backend.web.indicator.dto.IndicatorDTO;
import com.backend.web.indicator.entity.Indicator;
import com.backend.web.indicator.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;

    @Transactional(readOnly = true)
    public IndicatorDTO.IndicatorList find100Indicators() throws CustomException {
        List<Indicator> list = indicatorRepository.findByIdxBetween(19853, 19952);
        IndicatorDTO.IndicatorList indicatorList = new IndicatorDTO.IndicatorList();
        indicatorList.setList(list.stream().map((indicator) -> indicator.toIndicatorBasicDTO()).collect(Collectors.toList()));
        return indicatorList;
    }
}

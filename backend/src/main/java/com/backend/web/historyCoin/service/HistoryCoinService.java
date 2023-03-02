package com.backend.web.historyCoin.service;

import com.backend.common.model.CustomException;
import com.backend.web.historyCoin.dto.HistoryCoinDTO;
import com.backend.web.historyCoin.entity.HistoryCoin;
import com.backend.web.historyCoin.repository.HistoryCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryCoinService {

    private final HistoryCoinRepository historyCoinRepository;

    @Transactional(readOnly = true)
    public HistoryCoinDTO.CoinList find100HistoryCoins() throws CustomException {
        List<HistoryCoin> list = historyCoinRepository.findByIdxBetween(19853, 19952);
        HistoryCoinDTO.CoinList coinList = new HistoryCoinDTO.CoinList();
        coinList.setList(list.stream().map((coin) -> coin.toHistoryCoinBasicDTO()).collect(Collectors.toList()));
        return coinList;
    }


}

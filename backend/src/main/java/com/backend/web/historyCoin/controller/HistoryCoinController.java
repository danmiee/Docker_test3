package com.backend.web.historyCoin.controller;

import com.backend.common.model.ApiResponse;
import com.backend.common.model.CustomException;
import com.backend.common.util.ResponseMessageUtil;
import com.backend.web.historyCoin.service.HistoryCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HistoryCoinController {

    private final HistoryCoinService historyCoinService;

    @GetMapping("/api/historyCoins")
    public ResponseEntity<ApiResponse> find100HistoryCoins() {
        try {
            return ResponseMessageUtil.successMessage(historyCoinService.find100HistoryCoins());
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}

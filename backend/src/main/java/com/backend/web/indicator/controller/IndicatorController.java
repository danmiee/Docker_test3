package com.backend.web.indicator.controller;

import com.backend.common.model.ApiResponse;
import com.backend.common.model.CustomException;
import com.backend.common.util.ResponseMessageUtil;
import com.backend.web.indicator.service.IndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorService indicatorService;

    @GetMapping("/api/indicators")
    public ResponseEntity<ApiResponse> find100Indicators() {
        try {
            return ResponseMessageUtil.successMessage(indicatorService.find100Indicators());
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce);
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}

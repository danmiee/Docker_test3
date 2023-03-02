package com.backend.common.util;

import com.backend.common.model.ApiResponse;
import com.backend.common.model.StatusCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@Slf4j
public class ResponseMessageUtil {

    public static ResponseEntity<ApiResponse> successMessage() {
        return ResponseEntity.ok(new ApiResponse(StatusCode.CODE_200));
    }

    public static ResponseEntity<ApiResponse> successMessage(Object data) {
        return ResponseEntity.ok(new ApiResponse(StatusCode.CODE_200,data));
    }
    public static ResponseEntity<ApiResponse> errorMessage(StatusCode statusCode) {
        log.error("CustomException:: code: {}, message: {}", statusCode.getCode(), statusCode.getMsg());
        return ResponseEntity.status(statusCode.getCode()).body(new ApiResponse(statusCode));
    }
    public static ResponseEntity<ApiResponse> errorMessage(Exception e) {
        log.error("Exception:: code: {}, message: {}", StatusCode.CODE_500.getCode(), e.getMessage());
        return ResponseEntity.status(StatusCode.CODE_500.getCode()).body(new ApiResponse(StatusCode.CODE_500));
    }


}

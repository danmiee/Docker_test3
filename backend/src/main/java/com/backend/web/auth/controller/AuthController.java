package com.backend.web.auth.controller;

import com.backend.common.model.ApiResponse;
import com.backend.common.model.CustomException;
import com.backend.common.util.ResponseMessageUtil;
import com.backend.web.auth.dto.TokenDTO;
import com.backend.web.auth.dto.TokenRequestDTO;
import com.backend.web.auth.service.AuthService;
import com.backend.web.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인/재발급을 처리하는 API
 * SecurityConfig에서 /auth/** 요청은 전부 허용했기 때문에 토큰 검증 로직은 타지 않음
 * MemberDTO.SignIn에는 사용자가 로그인 시도한 loginId, password String이 존재
 * TokenRequestDTO에는 재발급을 위한 AccessToken, RefreshToken String이 존재
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/signIn")
    public ResponseEntity<ApiResponse> signIn(@RequestBody MemberDTO.SignIn signInInfo) {
        try {
            TokenDTO token = authService.signIn(signInInfo);
            return ResponseMessageUtil.successMessage(token);
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<ApiResponse> refresh(@RequestBody TokenRequestDTO tokenRequestDTO) {
        try {
            TokenDTO token = authService.refresh(tokenRequestDTO);
            return ResponseMessageUtil.successMessage(token);
//        } catch (CustomException ce) {
//            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}

package com.backend.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityContext에서 전역으로 유저 정보를 제공하는 유틸 클래스
 * JwtFilter에서 SecurityContext에 세팅한 유저 정보를 꺼냄
 * Member Idx를 저장하게 했으므로 꺼내서 Long타입으로 파싱하여 반환
 * SecurityContext는 ThreadLocal에 사용자의 정보를 저장
 */
@Slf4j
public class SecurityUtil {
    private SecurityUtil(){

    }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}

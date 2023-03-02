package com.backend.web.member.dto;

import com.backend.common.model.MemberType;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

public class MemberDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String loginId;
        private String nickname;
        private String password;
        private MemberType memberType;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Simple {
        private String loginId;
        private String nickname;
    }

    @Data
    public static class SignUp {
        private String loginId;
        private String nickname;
        private String password;
    }

    @Data
    public static class SignIn {
        private String loginId;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(loginId, password);
        }
    }
}

package com.backend.web.auth.service;

import com.backend.common.model.CustomException;
import com.backend.common.model.StatusCode;
import com.backend.common.util.CheckUtil;
import com.backend.config.jwt.JwtProvider;
import com.backend.web.auth.dto.TokenDTO;
import com.backend.web.auth.dto.TokenRequestDTO;
import com.backend.web.auth.entity.RefreshToken;
import com.backend.web.auth.repository.RefreshTokenRepository;
import com.backend.web.member.dto.MemberDTO;
import com.backend.web.member.entity.Member;
import com.backend.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Authentication
     * 사용자가 입력한 loginId, password 인증 정보 객체 UsernamePasswordAuthenticationToken을 생성
     * 아직 인증이 완료된 객체가 아니며 AuthenticationManager에서 authenticate 메서드의 파라미터로 넘겨서 검증 후에 Authentication을 받음
     * AuthenticationManager
     * 스프링 시큐리티에서 실제로 인증이 이루어지는 곳
     * authenticate 메서드 하나만 정의되어 있는 인터페이스이며 아래 코드에서는 Builder에서 UserDetails의 유저 정보가 서로 일치하는지 검사
     * 내부적으로 수행되는 검증 과정은 CustomUserDetailsService 클래스
     * 인증이 완료된 authentication 에는 Member Idx가 들어있음
     * 인증 객체를 바탕으로 AccessToken + RefreshToken을 생성
     * RefreshToken은 저장하고, 생성된 토큰 정보를 클라이언트에 전달
     */
    @Transactional
    public TokenDTO signIn(MemberDTO.SignIn signInInfo) throws CustomException{
        if (CheckUtil.isEmptyString(signInInfo.getLoginId())) {
            throw new CustomException(StatusCode.CODE_601);
        } else if (CheckUtil.isEmptyString(signInInfo.getPassword())) {
            throw new CustomException(StatusCode.CODE_603);
        }

        Member member = memberRepository.findByLoginId(signInInfo.getLoginId())
                .orElseThrow(()-> new CustomException(StatusCode.CODE_604));

            boolean isMatch = passwordEncoder.matches(signInInfo.getPassword(), member.getPassword());
            if (!isMatch) {
                throw new CustomException(StatusCode.CODE_605);
            } else {
                // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
                UsernamePasswordAuthenticationToken authenticationToken = signInInfo.toAuthentication();

                // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
                //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                // 3. 인증 정보를 기반으로 JWT 토큰 생성
                TokenDTO tokenDto = jwtProvider.generateTokenDto(authentication);

                // 4. RefreshToken 저장
                RefreshToken refreshToken = RefreshToken.builder()
                        .key(authentication.getName())
                        .value(tokenDto.getRefreshToken())
                        .build();

                refreshTokenRepository.save(refreshToken);

                // 5. 토큰 발급
                return tokenDto;
            }
    }

    /**
     * AccessToken + RefreshToken을 RequestBody에 받아서 검증
     * RefreshToken의 만료 여부를 먼저 검사
     * AccessToken을 복호화하여 유저 정보(Member Idx)를 가져오고 저장소에 있는 RefreshToken과 클라이언트가 전달한 RefreshToken의 일치 여부를 검사
     * 만약 일치한다면 로그인했을 때와 동일하게 새로운 토큰을 생성해서 클라이언트에 전달
     * RefreshToken은 재사용하지 못하게 저장소에서 값을 갱신
     */
    @Transactional
    public TokenDTO refresh(TokenRequestDTO tokenRequestDTO) {
        // 1. Refresh Token 검증
        if (!jwtProvider.validateToken(tokenRequestDTO.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDTO.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = jwtProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}

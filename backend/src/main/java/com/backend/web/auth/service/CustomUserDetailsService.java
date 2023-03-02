package com.backend.web.auth.service;

import com.backend.web.member.entity.Member;
import com.backend.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * UserDetailsService 인터페이스를 구현한 클래스
 * loadUserByUsername 메서드를 오버라이드하는데 여기서 넘겨받은 UserDetails와 Authentication의 패스워드를 비교하고 검증하는 로직을 처리
 * 물론 DB에서 username을 기반으로 값을 가져오기 때문에 아이디 존재 여부도 자동으로 검증
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberType().toString());

        return new User(
                String.valueOf(member.getIdx()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}

/**
 * 비밀번호 검증 과정
 * 1. AuthService에서 AuthenticationManagerBuilder를 주입 받음
 * 2. AuthenticationManagerBuilder에서 AuthenticationManager를 구현한 ProviderManager 생성
 * 3. ProviderManager는 AbstractUserDetailsAuthenticationProvider의 자식 클래스인 DaoAuthenticationProvider를 주입받아서 호출
 * 4. DaoAuthenticationProvider의 authenticate에서는 retrieveUser로 DB에 있는 사용자 정보를 가져오고 additionalAuthenticationChecks로 비밀번호 비교
 * 5. retrieveUser 내부에서 UserDetailsService 인터페이스를 직접 구현한 CustomUserDetailsService 클래스의 오버라이드 메서드인 loadUserByUsername가 호출됨
 */
package com.backend.web.member.controller;

import com.backend.common.util.SecurityUtil;
import com.backend.web.member.dto.MemberDTO;
import com.backend.web.member.service.MemberService;
import com.backend.common.model.ApiResponse;
import com.backend.common.model.CustomException;
import com.backend.common.util.ResponseMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member/signUp")
    public ResponseEntity<ApiResponse> signUp(@RequestBody MemberDTO.SignUp memberInfo) {
        try {
            memberService.createMember(memberInfo);
            return ResponseMessageUtil.successMessage();
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @GetMapping("/api/member/{idx}")
    public ResponseEntity<ApiResponse> findByMemberIdx(@PathVariable Long idx) {
        try {
            return ResponseMessageUtil.successMessage(memberService.findByMemberIdx(idx));
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    /**
     * 내 정보를 가져올 때는 SecurityUtil.getCurrentMemberId()를 사용
     * API 요청이 들어오면 필터에서 AccessToken을 복호화해서 유저 정보를 꺼내 SecurityContext에 저장
     * SecurityContext에 저장된 유저 정보는 전역으로 어디서든 꺼낼 수 있음
     * SecurityUtil 클래스에서는 유저 정보에서 Member Idx만 반환하는 메서드가 정의되어 있음
     * MemberService가 아닌 MemberController에서 사용하는 이유는 MemberService 테스트가 SecurityContext에 의존적이지 않게 하기 위함
     */
    @GetMapping("/api/member/me")
    public ResponseEntity<ApiResponse> findMeByMemberIdx() {
        try {
            return ResponseMessageUtil.successMessage(memberService.findByMemberIdx(SecurityUtil.getCurrentMemberId()));
        } catch (CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch (Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}

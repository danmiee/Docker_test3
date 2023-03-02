package com.backend.web.member.entity;

import com.backend.common.model.MemberType;
import com.backend.common.model.DateAudit;
import com.backend.web.member.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String loginId;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Builder
    public Member(Long idx, String loginId, String nickname, String password, MemberType memberType) {
        this.idx = idx;
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.memberType = memberType;
    }

    public MemberDTO.Basic toMemberBasicDTO() {
        return new ModelMapper().map(this, MemberDTO.Basic.class);
    }
    public MemberDTO.Simple toMemberSimpleDTO(){ return new ModelMapper().map(this, MemberDTO.Simple.class);}

}

package com.backend.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCode {
    CODE_200(200, "success")
    ,CODE_400(400, "Bad Request")
    ,CODE_500(500, "Internal Server Error")

    /* login, token */
    ,CODE_601(601, "로그인 아이디를 입력해주세요.")
    ,CODE_602(602, "닉네임을 입력해주세요.")
    ,CODE_603(603, "비밀번호를 입력해주세요.")
    ,CODE_604(604, "로그인 아이디가 일치하지 않습니다.")
    ,CODE_605(605, "비밀번호가 일치하지 않습니다.")
    ,CODE_606(606, "알 수 없는 로그인 정보입니다.")
    ,CODE_607(607, "이미 존재하는 아이디입니다.")
    ,CODE_608(608, "이미 존재하는 닉네임입니다.")
    ,CODE_609(609, "중복체크 타입이 올바르지 않습니다.")
    ,CODE_610(610, "현재 비밀번호와 일치하지 않습니다.")
    ,CODE_611(611, "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.")
    ,CODE_651(651, "토큰이 존재하지 않습니다.")
    ,CODE_652(652, "토큰 변환 중 오류가 발생했습니다.")
    ,CODE_653(653, "토큰이 만료되었습니다.")
    ,CODE_654(654, "Refresh 토큰이 아닙니다.")
    ,CODE_655(655, "Chain filter 중 오류가 발생했습니다.")

    /* user */
    ,CODE_701(701, "유저 인덱스가 올바르지 않습니다.")
    ,CODE_702(702, "유저 수정 내역이 존재하지 않습니다.")
    ,CODE_703(703, "프로필 사진 변경 중 오류가 발생했습니다.")
    ,CODE_704(704, "검색어를 입력해주세요.");

    private final Integer code;
    private final String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

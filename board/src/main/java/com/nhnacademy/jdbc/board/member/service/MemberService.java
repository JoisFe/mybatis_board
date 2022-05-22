package com.nhnacademy.jdbc.board.member.service;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.requestDto.LoginRequestDto;
import java.util.Optional;

public interface MemberService {
    Optional<Member> getMemberByMemberId(String memberId);
    Optional<Member> getMemberByMemberNum(Long memberNum);

    boolean matches(LoginRequestDto loginRequestDto);

//    boolean login()
}

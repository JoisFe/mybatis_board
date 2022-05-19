package com.nhnacademy.jdbc.board.member.service;

import com.nhnacademy.jdbc.board.member.domain.Member;
import java.util.Optional;

public interface MemberService {
    Optional<Member> getMemberByMemberId(String memberId);

    boolean matches(String memberId, String memberPwd);

//    boolean login()
}

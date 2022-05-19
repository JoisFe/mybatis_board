package com.nhnacademy.jdbc.board.member.mapper;

import com.nhnacademy.jdbc.board.member.domain.Member;
import java.util.Optional;

public interface MemberMapper {
    Optional<Member> selectMemberByMemberId(String memberId);
    Optional<Member> selectMemberByMemberNum(Long memberNum);
}

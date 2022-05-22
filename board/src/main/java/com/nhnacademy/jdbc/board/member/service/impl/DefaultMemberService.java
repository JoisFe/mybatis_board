package com.nhnacademy.jdbc.board.member.service.impl;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.mapper.MemberMapper;
import com.nhnacademy.jdbc.board.member.requestDto.LoginRequestDto;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultMemberService implements MemberService {
    private final MemberMapper memberMapper;

    public DefaultMemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Optional<Member> getMemberByMemberId(String memberId) {
        return memberMapper.selectMemberByMemberId(memberId);
    }

    @Override
    public Optional<Member> getMemberByMemberNum(Long memberNum) {
        return memberMapper.selectMemberByMemberNum(memberNum);
    }

    @Override
    public boolean matches(LoginRequestDto loginRequestDto) {
        Optional<Member> member = getMemberByMemberId(loginRequestDto.getMemberId());

        return member.map(x -> x.getMemberPwd().equals(loginRequestDto.getMemberPwd())).orElse(false);
    }
}

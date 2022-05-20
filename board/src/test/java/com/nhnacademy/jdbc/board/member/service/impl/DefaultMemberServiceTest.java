package com.nhnacademy.jdbc.board.member.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.jdbc.board.member.mapper.MemberMapper;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.member.web.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

class MemberIntegrationTest {
    private MockMvc mockMvc;
    private MemberService memberService;
    private MemberMapper memberMapper;
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        memberService = new DefaultMemberService(memberMapper);
        memberController = new MemberController(memberService);
    }

    @Test
    void getMemberByMemberIdTest() {
//        memberService.getMemberByMemberId();
    }

    @Test
    void matchTest() {

    }
}
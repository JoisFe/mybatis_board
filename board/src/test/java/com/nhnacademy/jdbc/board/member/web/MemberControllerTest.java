package com.nhnacademy.jdbc.board.member.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class MemberControllerTest {
    private MockMvc mockMvc;
    private MemberService memberService;
    private Member member;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        memberService = mock(MemberService.class);
        session = new MockHttpSession();
        member = new Member("admin", "adminadmin", MemberGrade.ADMIN);
        session.setAttribute("id", member.getMemberId());
        session.setAttribute("pwd", member.getMemberPwd());
        MemberController memberController = new MemberController(memberService);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("로그인 Get 요청 테스트")
    void loginGetMappingTest() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("loginForm"));
    }

    @Test
    @DisplayName("로그인 성공 여부 테스트")
    void loginTest() throws Exception {

        when(memberService.matches(any()))
            .thenReturn(true);

        mockMvc.perform(post("/login")
                .session(session)
                .param("memberId", "admin")
                .param("memberPwd", "adminadmin")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board"))
            .andReturn();
        assertThat(session.getAttribute("id")).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFailTest() throws Exception {
        when(memberService.matches(any()))
            .thenReturn(false);

        mockMvc.perform(post("/login")
                .session(session)
                .param("memberId", "admina")
                .param("mamberPwd", "aaa"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/login"))
            .andReturn();
    }

    @Test
    @DisplayName("로그인 validation 테스트")
    void loginValidationTest() throws Exception {
        MultiValueMap<String, String> loginFormValues = new LinkedMultiValueMap<>();
        loginFormValues.add("memberId", "");
        loginFormValues.add("memberPwd", "asdsds");

        session.clearAttributes();
        mockMvc.perform(post("/login").session(session)
                .params(loginFormValues))
            .andExpect(status().is3xxRedirection());
    }
}
package com.nhnacademy.jdbc.board.post.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.member.service.impl.DefaultMemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

class PostControllerTest {
    private MockMvc mockMvc;
    private PostService postService;
    private MemberService memberService;
    private CommentService commentService;
    private Member member;
    private Post post;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {



        postService = mock(PostService.class);
        memberService = mock(MemberService.class);
        commentService = mock(CommentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService, memberService, commentService)).build();

        session = new MockHttpSession();
        session.setAttribute("id", "admin");
        session.setAttribute("pwd", "adminadmin");
        member = new Member("admin", "adminadmin", MemberGrade.ADMIN);
    }

    @Test
    void getPostListViewTest() throws Exception {
        when(memberService.matches(any(), any()))
            .thenReturn(true);

        mockMvc.perform(get("/board"))
            .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void postRegister() {
    }

    @Test
    void testPostRegister() {
    }

    @Test
    void postDetail() {
    }

    @Test
    void postModify() {
    }

    @Test
    void testPostModify() {
    }

    @Test
    void postDelete() {
    }
}
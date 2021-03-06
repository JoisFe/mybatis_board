package com.nhnacademy.jdbc.board.post.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.like.service.LoveService;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


class PostControllerTest {
    private MockMvc mockMvc;
    private PostService postService;
    private MemberService memberService;
    private CommentService commentService;
    private LoveService loveService;
    private Member member;
    private Post post;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        memberService = mock(MemberService.class);
        commentService = mock(CommentService.class);
        loveService = mock(LoveService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
            new PostController(postService, memberService, commentService, loveService)).build();

        session = new MockHttpSession();
        session.setAttribute("id", "admin");
        session.setAttribute("pwd", "adminadmin");
        member = new Member(1L, "admin", "adminadmin", MemberGrade.ADMIN);

        post = new Post(1L, "test", "test", new Date(), null, 0, null, null);
//        postService.insertPost(post);
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("????????? ????????? ?????? ????????? ????????? ?????? ?????? ?????? ?????????")
    void getPostListViewTestWithoutLogin() throws Exception {
        session.clearAttributes();
        verify(memberService, never()).getMemberByMemberId(any());

        mockMvc.perform(get("/board"))
            .andExpect(status().isOk())
            .andExpect(view().name("boardView"))
            .andExpect(model().attributeExists("posts"))
            .andExpect(model().attributeExists("paging"));
    }

    @Test
    @DisplayName("????????? ??????????????? DB?????? ????????? ???????????? ?????? ?????? ?????????????????????")
    void MemberNotFoundExceptionTest() throws Exception {
        assertThatThrownBy(() -> mockMvc.perform(get("/board")
            .session(session))).hasMessageContaining("?????? ????????? ???????????? ????????????.");
    }

    @Test
    @DisplayName("????????? ?????? ?????? ????????? ????????? model??? sessionId??? ??????????????? ?????????")
    void getPostListWithLogintest() throws Exception {
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(get("/board")
                .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("boardView"))
            .andExpect(model().attributeExists("posts"))
            .andExpect(model().attributeExists("paging"));

        verify(memberService, atLeastOnce()).getMemberByMemberId(any());
    }


    @Test
    @DisplayName("????????? ?????? ?????????")
    void getPostDetailTest() throws Exception {
        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(get("/post/detail/{postNum}", 1))
            .andExpect(status().isOk())
            .andExpect(view().name("postDetail"));
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????????")
    void postRegisterSuccessTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("postTitle", "???????????????");
        params.add("postContent", "??????");
        params.add("multipartFile", "");

        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(post("/post/register")
                .session(session)
                .params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("????????? ?????? GET ?????? ?????????")
    void postModifyGetMappingTest() throws Exception {
        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));

        mockMvc.perform(get("/post/modify/{postNum}", 1)
                .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("postModify"));
    }

    @Test
    @DisplayName("????????? ?????? Post ?????? ?????????")
    void postModifyPostMappingTest() throws Exception {
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(post("/post/modify/{postNum}", 1)
                .session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board"));

        verify(postService, atLeastOnce()).modifyPost(any(), any(), any(), any());
    }

    @Test
    @DisplayName("????????? ?????? ?????????")
    void postDeleteTest() throws Exception {
        mockMvc.perform(get("/post/delete/1")
                .session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board"));

        verify(postService, atLeastOnce()).matchCheckSessionIdAndWriterId(any(), any());
        verify(postService, atLeastOnce()).deletePost(any(), any());
    }

    @Test
    @DisplayName("admin?????? ??????????????? ?????? ?????????")
    void postRestoreVisitWithAdminTest() throws Exception {
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(get("/post/deleteRestore")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("posts"))
            .andExpect(model().attributeExists("paging"));

        verify(memberService, atLeastOnce()).getMemberByMemberId(any());
    }

    @Test
    @DisplayName("?????? ???????????? ??????????????? ????????? ???????????? ?????????")
    void postRestoreVisitWithoutAdminTest() {
        Member user = new Member(2L, "user", "useruser", MemberGrade.USER);
        session.clearAttributes();
        session.setAttribute("user", "useruser");

        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(user));

        assertThatThrownBy(() -> mockMvc.perform(get("/post/deleteRestore")))
            .hasMessageContaining("????????? ????????? ???????????? ?????? ???????????????");
    }

    @Test
    @DisplayName("????????? ????????? view??? ????????? ??????????????? ?????????")
    void postSearchTest() throws Exception {
        mockMvc.perform(post("/board/search")
                .param("searchValue", "?????????"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board/search/?????????"));
    }

    @Test
    @DisplayName("????????? ?????? ????????? ????????? ??????????????? ?????????")
    void postSearchGetTest() throws Exception {
        mockMvc.perform(get("/board/search/{searchVal}", "turtle"))
            .andExpect(status().isOk())
            .andExpect(view().name("boardView"));
    }
}
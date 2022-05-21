package com.nhnacademy.jdbc.board.post.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.Date;
import java.util.Optional;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


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
        mockMvc = MockMvcBuilders.standaloneSetup(
            new PostController(postService, memberService, commentService)).build();

        session = new MockHttpSession();
        session.setAttribute("id", "admin");
        session.setAttribute("pwd", "adminadmin");
        member = new Member(1L, "admin", "adminadmin", MemberGrade.ADMIN);

        post = new Post(1L, "test", "test", new Date(), null, 0, null);
//        postService.insertPost(post);
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("로그인 안했을 경우 게시판 게시글 전체 목록 조회 테스트")
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
    @DisplayName("세션은 존재하는데 DB에서 회원이 존재하지 않을 경우 예외처리테스트")
    void MemberNotFoundExceptionTest() throws Exception {
        assertThatThrownBy(() ->mockMvc.perform(get("/board")
            .session(session))).hasMessageContaining("해당 회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 했을 경우 게시판 조회시 model에 sessionId가 추가되는지 테스트")
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
    @DisplayName("게시글 조회 테스트")
    void getPostDetailTest() throws Exception {
        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(get("/post/detail/{postNum}", 1))
            .andExpect(status().isOk())
            .andExpect(view().name("postDetail"));
    }

    @Test
    @DisplayName("게시물 등록 성공 테스트")
    void postRegisterSuccessTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("postTitle", "제발가즈아");
        params.add("postContent", "뿌슝");

        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(post("/post/register")
                .session(session)
                .params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 수정 GET 요청 테스트")
    void postModifyGetMappingTest() throws Exception {
        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));

        mockMvc.perform(get("/post/modify/{postNum}", 1)
                .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("postModify"));
    }

    @Test
    @DisplayName("게시글 수정 Post 요청 테스트")
    void postModifyPostMappingTest() throws Exception {
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(post("/post/modify/{postNum}", 1)
            .session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board"));

        verify(postService, atLeastOnce()).modifyPost(any(), any(), any(), any());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void postDelete() throws Exception {
        mockMvc.perform(get("/post/delete/1")
                .session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/board"));

        verify(postService, atLeastOnce()).matchCheckSessionIdAndWriterId(any(), any());
        verify(postService, atLeastOnce()).deletePost(any(), any());
    }
}
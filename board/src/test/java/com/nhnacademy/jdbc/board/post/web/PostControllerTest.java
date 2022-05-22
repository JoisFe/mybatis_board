package com.nhnacademy.jdbc.board.post.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.like.service.LoveService;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextHierarchy({
//    @ContextConfiguration(classes = {RootConfig.class}),
//    @ContextConfiguration(classes = {WebConfig.class})
//})
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

        post = new Post(1L, "test", "test", new Date(), null, 0, null);
//        postService.insertPost(post);
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("게시판 게시글 전체 목록 조회 테스트")
    void getPostListViewTest() throws Exception {
        mockMvc.perform(get("/board"))
            .andExpect(status().isOk())
            .andExpect(view().name("boardView"))
            .andExpect(model().attributeExists("posts"))
            .andExpect(model().attributeExists("paging"));
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
    @DisplayName("게시글 수정 조회페이지 테스트")
    void postModifyGetMappingTest() throws Exception {
        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));

        mockMvc.perform(get("/post/modify/{postNum}", 1)
            .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("postModify"));
    }

    @Test
    void postModifyPostMappingTest() {
    }

    @Test
    void postDelete() {
    }
}
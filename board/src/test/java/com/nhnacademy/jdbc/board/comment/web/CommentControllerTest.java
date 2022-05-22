package com.nhnacademy.jdbc.board.comment.web;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.requestDto.PostRequestDto;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.Date;
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

public class CommentControllerTest {
    private MockMvc mockMvc;
    private PostService postService;
    private MemberService memberService;
    private CommentService commentService;
    private Member member;
    private Post post;
    private MockHttpSession session;
    private Comment comment;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        memberService = mock(MemberService.class);
        commentService = mock(CommentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
            new CommentController(commentService, postService, memberService)).build();


        session = new MockHttpSession();
        session.setAttribute("id", "admin");
        session.setAttribute("pwd", "adminadmin");

        member = new Member(1L, "admin", "adminadmin", MemberGrade.ADMIN);
        post = new Post(1L, 1L, "test", "test", new Date(), null, 0, null);
        comment = new Comment(1L, 1L, 1L, "테스트코멘트", now());
        postService.insertPost(new PostRequestDto(post.getPostTitle(), post.getPostContent()), 1L,
            null);
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("한 게시물에 대한 댓글 등록 테스트")
    void commentRegisterTest() throws Exception {
        MultiValueMap<String, String> commentContent = new LinkedMultiValueMap<>();
        commentContent.add("commentContent", "댓글입니당..");

        when(postService.getPostByPostNum(any())).thenReturn(Optional.ofNullable(post));
        when(memberService.getMemberByMemberId(any())).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(post("/comment/register/{postNum}", 1)
                .session(session)
                .params(commentContent))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/detail/1"));

        verify(commentService, atLeastOnce()).insertComment(any(), any(),any());
    }

}

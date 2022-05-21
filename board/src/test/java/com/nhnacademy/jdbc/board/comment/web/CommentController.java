package com.nhnacademy.jdbc.board.comment.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({@ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)})
public class CommentController {
    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    WebApplicationContext webApplicationContext;

    public CommentController(
        WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockHttpSession = new MockHttpSession();
    }

    @Test
    @DisplayName("한 게시물에 대한 댓글 등록 테스트")
    void commentRegister() throws Exception {
        MultiValueMap<String, String> commentContent = new LinkedMultiValueMap<>();
        commentContent.add("commentContent", "댓글입니당..");

        mockHttpSession.setAttribute("id", "admin");

        Member member = new Member(1L, "admin", "adminadmin", MemberGrade.ADMIN);

        MemberService memberService = mock(MemberService.class);
        when(memberService.getMemberByMemberId(any()))
            .thenReturn(Optional.of(member));

        CommentService commentService = mock(CommentService.class);
        //when(commentService.insertComment(any(), anyLong(), anyLong())).then();


        mockMvc.perform(post("/comment/register/{postNum}", 1)
                .session(mockHttpSession)
                .params(commentContent))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("postDetail"));
    }
}

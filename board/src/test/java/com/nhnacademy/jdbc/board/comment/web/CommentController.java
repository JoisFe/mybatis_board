package com.nhnacademy.jdbc.board.comment.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> commentContent = new HashMap<>();
        commentContent.put("")
        mockHttpSession.setAttribute("sessionId", "admin");
        mockMvc.perform(post("/comment/register/{postNum}")
            .session(mockHttpSession)
            .params())
            .andExpect(status().is3xxRedirection())
    }
}

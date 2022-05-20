package com.nhnacademy.jdbctest.board.board.board;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.index.web.IndexController;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class IndexControllerTest {
    private MockMvc mockMvc;
    private DefaultUserService defaultUserService;

    @BeforeEach
    void setUp(){
        defaultUserService = mock(DefaultUserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new IndexController())
            .build();
    }

    @Test
    void indexGetTest() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index/index"));
    }

}

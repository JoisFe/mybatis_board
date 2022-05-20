package com.nhnacademy.jdbc.board.member.temp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.web.UserLoginController;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserLoginControllerTest {
    private MockMvc mockMvc;
    private UserService userService;
    private User user;
    private MockHttpSession session;
    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        user = new User(1,"admin",null,false);
        session = new MockHttpSession();
        session.setAttribute("login",user);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserLoginController(userService))
            .build();
    }


    @Test
    void userDoLoginTestIsSuccessful() throws Exception {
        when(userService.login("user01", "1234"))
            .thenReturn(Optional.of(user));

        MvcResult mvcResult = mockMvc.perform(post("/login").session(session)
                .param("id", "user01")
                .param("pwd", "1234"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/postView/0"))
            .andReturn();

        assertThat((User)session.getAttribute("login")).isEqualTo(user);
    }

    @Test
    void userDoLoginTestIsNotSuccessful() throws Exception {
        when(userService.login(anyString(), anyString()))
            .thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .param("id", "userssss")
                .param("pwd", "12345"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"))
            .andReturn();

        HttpSession httpSession = mvcResult.getRequest().getSession(false);
        assertThat(httpSession.getAttribute("login")).isNull();
    }

}
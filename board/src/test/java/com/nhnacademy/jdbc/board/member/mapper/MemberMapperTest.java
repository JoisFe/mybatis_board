package com.nhnacademy.jdbc.board.member.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.nhnacademy.jdbc.board.config.MybatisConfig;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebAppInitializer;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = { RootConfig.class }),
    @ContextConfiguration(classes = WebConfig.class),
    @ContextConfiguration(classes = MybatisConfig.class),
    @ContextConfiguration(classes = WebAppInitializer.class)
})

class MemberMapperTest {
    private MemberMapper memberMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        memberMapper = mock(MemberMapper.class);
        Member member = new Member("admin", "adminadmin", MemberGrade.ADMIN);
    }

    @Test
    void selectMemberByMemberIdTest() {

    }

    @Test
    void selectMemberByMemberNum() {
    }
}
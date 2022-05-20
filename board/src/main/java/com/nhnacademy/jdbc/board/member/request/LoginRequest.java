package com.nhnacademy.jdbc.board.member.request;

import lombok.Data;
import lombok.Value;

@Value
public class LoginRequest {
    String memberId;
    String memberPwd;
}

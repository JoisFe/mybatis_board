package com.nhnacademy.jdbc.request;

import lombok.Data;
import lombok.Value;

@Value
public class LoginRequest {
    String memberId;
    String memberPwd;


}

package com.nhnacademy.jdbc.request;

import lombok.Value;

@Value
public class PostRegisterRequest {
    String postTitle;
    String postContent;
}

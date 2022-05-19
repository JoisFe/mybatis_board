package com.nhnacademy.jdbc.request;

import lombok.Value;

@Value
public class PostRequest {
    String postTitle;
    String postContent;
}

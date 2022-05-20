package com.nhnacademy.jdbc.board.comment.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CommentRequest {
     @NotBlank
     String commentContent;
}

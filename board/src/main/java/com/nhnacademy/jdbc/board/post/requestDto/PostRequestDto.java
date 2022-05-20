package com.nhnacademy.jdbc.board.post.requestDto;

import javax.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class PostRequestDto {
    @NotNull @Length(max=8)
    String postTitle;
    @NotNull @Length(max=75)
    String postContent;
}

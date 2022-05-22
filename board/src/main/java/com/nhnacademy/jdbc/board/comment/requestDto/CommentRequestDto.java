package com.nhnacademy.jdbc.board.comment.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class CommentRequestDto {
     @NotBlank @Length(max=25)
     String commentContent;
}

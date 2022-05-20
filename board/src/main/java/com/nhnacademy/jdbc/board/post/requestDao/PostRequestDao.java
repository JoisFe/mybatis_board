package com.nhnacademy.jdbc.board.post.requestDao;

import javax.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class PostRequestDao {
    @NotNull @Length(min=1, max=8)
    String postTitle;
    @NotNull @Length(min=1, max=75)
    String postContent;
}

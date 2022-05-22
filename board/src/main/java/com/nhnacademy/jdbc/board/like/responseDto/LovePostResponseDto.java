package com.nhnacademy.jdbc.board.like.responseDto;

import java.util.Date;
import lombok.Value;

@Value
public class LovePostResponseDto {
    Long memberNum;
    Long postNum;
    Long postNum2;
    Long memberNum2;
    String postTitle;
    String postContent;
    Date createdDate;
    Date modifiedDate;
    Integer deleteCheck;
    Long modifyMemberNum;
}

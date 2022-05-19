package com.nhnacademy.jdbc.board.comment.domain;

import java.util.Date;

public class Comment {
    private Long commentNum;
    private final Long memberNum;
    private final Long postNum;
    private final String commentContent;
    private final Date commentDate;

    public Comment(Long memberNum, Long postNum, String commentContent, Date commentDate) {
        this.memberNum = memberNum;
        this.postNum = postNum;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public Comment(Long commentNum, Long memberNum, Long postNum, String commentContent,
                   Date commentDate) {
        this.commentNum = commentNum;
        this.memberNum = memberNum;
        this.postNum = postNum;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "commentNum=" + commentNum +
            ", memberNum=" + memberNum +
            ", postNum=" + postNum +
            ", commentContent='" + commentContent + '\'' +
            ", commentDate=" + commentDate +
            '}';
    }
}

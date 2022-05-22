package com.nhnacademy.jdbc.board.comment.domain;

import java.util.Date;

public class Comment {
    private Long commentNum;
    private final Long postNum;
    private final Long memberNum;
    private final String commentContent;
    private final Date commentDate;

    public Comment(Long postNum, Long memberNum, String commentContent, Date commentDate) {
        this.postNum = postNum;
        this.memberNum = memberNum;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public Comment(Long commentNum, Long postNum, Long memberNum, String commentContent,
                   Date commentDate) {
        this.commentNum = commentNum;
        this.postNum = postNum;
        this.memberNum = memberNum;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public Long getMemberNum() {
        return memberNum;
    }

    public Long getPostNum() {
        return postNum;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Date getCommentDate() {
        return commentDate;
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

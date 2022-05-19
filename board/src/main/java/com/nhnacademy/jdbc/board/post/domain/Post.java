package com.nhnacademy.jdbc.board.post.domain;

import java.util.Date;

public class Post {
    private Long postNum;
    private final Long memberNum;
    private final String postTitle;
    private final String postContent;
    private final Date createdDate;
    private Date modifiedDate;
    private Integer deleteCheck;

    public Post(Long memberNum, String postTitle, String postContent,
                Date createdDate, Integer deleteCheck) {
        this.memberNum = memberNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.deleteCheck = deleteCheck;
    }

    public Post(Long postNum, Long memberNum, String postTitle, String postContent,
                Date createdDate, Date modifiedDate, Integer deleteCheck) {
        this.postNum = postNum;
        this.memberNum = memberNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteCheck = deleteCheck;
    }

    public Long getPostNum() {
        return postNum;
    }

    public Long getMemberNum() {
        return memberNum;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Integer getDeleteCheck() {
        return deleteCheck;
    }

    @Override
    public String toString() {
        return "Post{" +
            "postNum=" + postNum +
            ", memberNum=" + memberNum +
            ", postTitle='" + postTitle + '\'' +
            ", postContent='" + postContent + '\'' +
            ", createdDate=" + createdDate +
            ", modifiedDate=" + modifiedDate +
            ", deleteCheck=" + deleteCheck +
            '}';
    }
}

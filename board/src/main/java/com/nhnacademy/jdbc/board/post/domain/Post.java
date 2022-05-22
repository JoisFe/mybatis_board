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
    private Long modifyMemberNum;
    private String fileName;

    public Post(Long postNum, Long memberNum, String postTitle, String postContent,
                Date createdDate, Date modifiedDate, Integer deleteCheck,
                Long modifyMemberNum, String fileName) {
        this.postNum = postNum;
        this.memberNum = memberNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteCheck = deleteCheck;
        this.modifyMemberNum = modifyMemberNum;
        this.fileName = fileName;
    }

    public Post(Long memberNum, String postTitle, String postContent, Date createdDate,
                Date modifiedDate, Integer deleteCheck, Long modifyMemberNum, String fileName) {
        this.memberNum = memberNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteCheck = deleteCheck;
        this.modifyMemberNum = modifyMemberNum;
        this.fileName = fileName;
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

    public Long getModifyMemberNum() {
        return modifyMemberNum;
    }

    public String getFileName() {
        return fileName;
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
            ", modifyMemberNum=" + modifyMemberNum +
            '}';
    }
}

package com.nhnacademy.jdbc.board.like.domain;

public class Love {
    private Long memberNum;
    private Long postNum;

    public Love(Long memberNum, Long postNum) {
        this.memberNum = memberNum;
        this.postNum = postNum;
    }

    public Long getMemberNum() {
        return memberNum;
    }

    public Long getPostNum() {
        return postNum;
    }
}

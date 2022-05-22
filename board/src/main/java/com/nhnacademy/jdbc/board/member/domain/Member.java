package com.nhnacademy.jdbc.board.member.domain;

public class Member {
    private Long memberNum;
    private final String memberId;
    private final String memberPwd;
    private final MemberGrade memberGrade;

    public Member(String memberId, String memberPwd,
                  MemberGrade memberGrade) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberGrade = memberGrade;
    }

    public Member(Long memberNum, String memberId, String memberPwd,
                  MemberGrade memberGrade) {
        this.memberNum = memberNum;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberGrade = memberGrade;
    }

    public Long getMemberNum() {
        return memberNum;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public MemberGrade getMemberGrade() {
        return memberGrade;
    }
}

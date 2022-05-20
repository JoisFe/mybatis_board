package com.nhnacademy.jdbc.board.post.respondDto;

import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import java.util.Date;
import lombok.Getter;

@Getter
public class BoardRespondDto {
    Long postNum;
    Long memberNum;
    String postTitle;
    String postContent;
    Date createdDate;
    Date modifiedDate;
    Integer deleteCheck;
    Long modifyMemberNum;
    Long memberNum2;
    String memberId;
    String memberPwd;
    MemberGrade memberGrade;
    String modifyMemberId;
    Long commentCount;

    public BoardRespondDto(Long postNum, Long memberNum, String postTitle, String postContent,
                           Date createdDate, Date modifiedDate, Integer deleteCheck,
                           Long modifyMemberNum, Long memberNum2, String memberId,
                           String memberPwd,
                           MemberGrade memberGrade) {
        this.postNum = postNum;
        this.memberNum = memberNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteCheck = deleteCheck;
        this.modifyMemberNum = modifyMemberNum;
        this.memberNum2 = memberNum2;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberGrade = memberGrade;
    }

    public void setModifyMemberId(String modifyMemberId) {
        this.modifyMemberId = modifyMemberId;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}

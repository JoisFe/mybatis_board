package com.nhnacademy.jdbc.board.comment.service;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.requestDto.CommentRequestDto;
import java.util.List;

public interface CommentService {
    List<Comment> getComments(Long postNum);
    void insertComment(CommentRequestDto commentRegisterRequest, Long postNum, Long memberNum);

    void modifyComment(String commentContent, Long commentNum);
    void deleteComment(Long commentNum);
}

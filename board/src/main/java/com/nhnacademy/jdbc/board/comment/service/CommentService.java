package com.nhnacademy.jdbc.board.comment.service;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getComments(Long postNum);
    void insertComment(Comment comment);
    void modifyComment(String commentContent, Long commentNum);
    void deleteComment(Long commentNum);
}

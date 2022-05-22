package com.nhnacademy.jdbc.board.comment.mapper;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import java.util.List;

public interface CommentMapper {
    List<Comment> selectCommentsByPostNum(Long postNum);
    void insertComment(Comment comment);
    void updateCommentContentByCommentNum(String commentContent, Long commentNum);
    void deleteByCommentNum(Long commentNum);
}

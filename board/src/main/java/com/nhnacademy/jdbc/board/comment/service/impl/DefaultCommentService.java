package com.nhnacademy.jdbc.board.comment.service.impl;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.mapper.CommentMapper;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentService implements CommentService {
    private final CommentMapper commentMapper;

    DefaultCommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> getComments(Long postNum) {
        return commentMapper.selectCommentsByPostNum(postNum);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void modifyComment(String commentContent, Long commentNum) {
        commentMapper.updateCommentContentByCommentNum(commentContent, commentNum);
    }

    @Override
    public void deleteComment(Long commentNum) {
        commentMapper.deleteByCommentNum(commentNum);
    }
}

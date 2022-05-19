package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DafaultPostService implements PostService {
    private final PostMapper postMapper;

    DafaultPostService (PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> getPostByPostNum(Long postNum) {
        return Optional.ofNullable(postMapper.selectPostByPostNum(postNum)
            .orElseThrow(() -> new PostNotFoundException("해당 번호의 게시글이 존재하지 않습니다.")));
    }

    @Override
    public List<Post> getPosts(Integer deleteCheck) {
        return postMapper.selectPosts(deleteCheck);
    }

    @Override
    public void insertPost(Post post) {
        postMapper.insertPost(post);
    }

    @Override
    public void modifyPost(String postTitle, String postContent, Long postNum) {
        postMapper.updatePostByPostNum(postTitle, postContent, postNum);
    }

    @Override
    public void deletePost(Long postNum) {
        postMapper.deletePostByPostNum(postNum);
    }
}

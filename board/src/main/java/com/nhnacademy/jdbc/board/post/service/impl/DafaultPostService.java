package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DafaultPostService implements PostService {
    private final PostMapper postMapper;

    DafaultPostService (PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> getPostByPostNum(Long postNum) {
        return postMapper.selectPostByPostNum(postNum);
    }

    @Override
    public List<Post> getPosts(Integer deleteCheck) {
        return postMapper.selectPosts(deleteCheck);
    }

    @Override
    public void insertPost(Post post) {

    }

    @Override
    public void modifyPost(Long postNum, String postTitle, String postContent) {

    }

    @Override
    public void deletePost(Long postNum) {

    }
}

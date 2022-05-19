package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DafaultPostService implements PostService {
    private final static int NUM_PER_PAGE = 20;
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
    public List<Post> getPosts(Integer deleteCheck, int page) {
        int startRowPerPage = (page - 1) * NUM_PER_PAGE;
        return postMapper.selectPosts(deleteCheck, startRowPerPage, NUM_PER_PAGE);
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

    @Override
    public Long getPostSize(Integer deleteCheck) {
        return postMapper.findTotalPostsCount(deleteCheck);
    }

    @Override
    public int getPageSize(Integer deleteCheck) {
        return (int) Math.ceil(getPostSize(deleteCheck) / NUM_PER_PAGE) + 1;
    }
}

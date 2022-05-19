package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getPostByPostNum(Long postNum);
    List<Post> getPosts(Integer deleteCheck);
    void insertPost(Post post);
    void modifyPost(String postTitle, String postContent, Long postNum);
    void deletePost(Long postNum);
}

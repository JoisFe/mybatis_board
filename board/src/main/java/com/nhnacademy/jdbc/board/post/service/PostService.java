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
// 서비스 안쪽에는 비지니스로직
// 유저서비스 안에는 getuser기능을 해주는 메소드. post에서 호출.

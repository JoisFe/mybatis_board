package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface PostMapper {
    Optional<Post> selectPostByPostNum(Long postNum);
    List<Post> selectPosts(Integer deleteCheck);
    void insertPost(Post post);
    void updatePostByPostNum(Long postNum, String postTitle, String postContent);
    void deletePostByPostNum(Long postNum);
}

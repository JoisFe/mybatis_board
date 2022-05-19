package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {
    Optional<Post> selectPostByPostNum(Long postNum);
    List<Post> selectPosts(@Param("deleteCheck") Integer deleteCheck, @Param("startRowPerPage") int startRowPerPage, @Param("numPerPage") int numPerPage);
    Long findTotalPostsCount(Integer deleteCheck);
    void insertPost(Post post);
    void updatePostByPostNum(@Param("postTitle") String postTitle, @Param("postContent") String postContent, @Param("postNum") Long postNum);
    void deletePostByPostNum(Long postNum);
}

package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {
    Optional<Post> selectPostByPostNum(Long postNum);
    List<BoardRespondDto> selectPosts(@Param("deleteCheck") Integer deleteCheck, @Param("startRowPerPage") int startRowPerPage, @Param("numPerPage") int numPerPage);
    Integer findTotalPostsCount(Integer deleteCheck);
    void insertPost(Post post);
    void updatePostByPostNum(@Param("postTitle") String postTitle, @Param("postContent") String postContent,
                             @Param("postNum") Long postNum, @Param("memberNum") Long memberNum);
    void deletePostByPostNum(@Param("deleteCheck") Integer deleteCheck, @Param("postNum") Long postNum);
    Optional<Member> selectMemberByPostNum(Long postNum);
    Long findCommentCountByPostNum(Long postNum);

    void updateDeletePostRestore(@Param("deleteCheck") Integer deleteCheck, @Param("postNum") Long postNum);

    //검색기능 추가
    List<BoardRespondDto> selectPostsBySearch(@Param("deleteCheck") Integer deleteCheck, @Param("startRowPerPage") int startRowPerPage, @Param("numPerPage") int numPerPage, @Param("searchValue") String searchValue);

}

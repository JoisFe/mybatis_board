package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.requestDto.PostRequestDto;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getPostByPostNum(Long postNum);
    List<BoardRespondDto> getPosts(Integer deleteCheck, int page);
    void insertPost(PostRequestDto postRegisterRequest, Long memberNum);

    void modifyPost(String postTitle, String postContent, Long postNum, Long memberNum);
    void deletePost(Integer deleteCheck, Long postNum);
    int getPostSize(Integer deleteCheck);
    int getPageSize(Integer deleteCheck);
    void matchCheckSessionIdAndWriterId(Long postNum, String sessionId);
    Optional<Member> findWriterIdPostNum(Long postNum);
    Long getCommentSize(Long postNum);
    void restorePostByPostNum(Long postNum, String sessionId);

    //검색기능 추가
    List<BoardRespondDto> getPostsWithSearch(Integer deleteCheck, int page, String searchValue);
}

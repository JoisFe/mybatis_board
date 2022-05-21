package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.mapper.MemberMapper;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.requestDto.PostRequestDto;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.exception.NotMatchMemberIdException;
import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DafaultPostService implements PostService {
    private static final int DELETE_STATE = 1;
    private static final int NOT_DELETE_STATE = 0;

    private static final int NUM_PER_PAGE = 20;

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    public DafaultPostService(PostMapper postMapper,
                              MemberMapper memberMapper) {
        this.postMapper = postMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public Optional<Post> getPostByPostNum(Long postNum) {
        return Optional.ofNullable(postMapper.selectPostByPostNum(postNum)
            .orElseThrow(() -> new PostNotFoundException("해당 번호의 게시글이 존재하지 않습니다.")));
    }

    @Override
    public List<BoardRespondDto> getPosts(Integer deleteCheck, int page) {
        int startRowPerPage = (page - 1) * NUM_PER_PAGE;
        List<BoardRespondDto> boardResponds =
            postMapper.selectPosts(deleteCheck, startRowPerPage, NUM_PER_PAGE);

        for (BoardRespondDto boardRespond : boardResponds) {
            Long modifyMemberNum = boardRespond.getModifyMemberNum();
            String modifyMemberId = null;
            if (modifyMemberNum != null) {
                modifyMemberId = memberMapper.selectMemberByMemberNum(modifyMemberNum)
                    .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."))
                    .getMemberId();
            }

            boardRespond.setModifyMemberId(modifyMemberId);
            boardRespond.setCommentCount(postMapper.findCommentCountByPostNum(
                boardRespond.getPostNum()));
        }

        return boardResponds;
    }

    @Override
    public void insertPost(PostRequestDto postRegisterRequest, Long memberNum) {

        Post post = new Post(
            memberNum,
            postRegisterRequest.getPostTitle(),
            postRegisterRequest.getPostContent(),
            new Date(),
            null,
            NOT_DELETE_STATE,
            null
        );

        postMapper.insertPost(post);
    }

    @Override
    public void modifyPost(String postTitle, String postContent, Long postNum, Long memberNum) {
        postMapper.updatePostByPostNum(postTitle, postContent, postNum, memberNum);
    }

    @Override
    public void deletePost(Integer deleteCheck, Long postNum) {
        postMapper.deletePostByPostNum(deleteCheck, postNum);
    }

    @Override
    public int getPostSize(Integer deleteCheck) {
        return postMapper.findTotalPostsCount(deleteCheck);
    }

    @Override
    public int getPageSize(Integer deleteCheck) {
        return (int) Math.ceil(getPostSize(deleteCheck) / NUM_PER_PAGE) + 1;
    }

    @Override
    public void matchCheckSessionIdAndWriterId(Long postNum, String sessionId) {
        if (memberMapper.selectMemberByMemberId(sessionId)
            .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."))
            .getMemberGrade().equals(MemberGrade.ADMIN)) {
            return;
        }

        if (!findWriterIdPostNum(postNum)
            .orElseThrow(() -> new MemberNotFoundException("해당 번호의 회원이 존재하지 않습니다."))
            .getMemberId().equals(sessionId)) {
            throw new NotMatchMemberIdException("로그인한 아이디와 작성자 아이디가 다릅니다.");
        }
    }

    @Override
    public Optional<Member> findWriterIdPostNum(Long postNum) {
        return postMapper.selectMemberByPostNum(postNum);
    }

    @Override
    public Long getCommentSize(Long postNum) {
        return postMapper.findCommentCountByPostNum(postNum);
    }
}

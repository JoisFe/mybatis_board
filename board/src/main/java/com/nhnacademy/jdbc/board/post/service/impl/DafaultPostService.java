package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.mapper.MemberMapper;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.respondDao.BoardRespondDao;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.exception.NotMatchMemberIdException;
import com.nhnacademy.jdbc.exception.PostNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DafaultPostService implements PostService {
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
    public List<BoardRespondDao> getPosts(Integer deleteCheck, int page) {
        int startRowPerPage = (page - 1) * NUM_PER_PAGE;
        List<BoardRespondDao> boardResponds =
            postMapper.selectPosts(deleteCheck, startRowPerPage, NUM_PER_PAGE);

        for (BoardRespondDao boardRespond : boardResponds) {
            Long modifyMemberNum = boardRespond.getModifyMemberNum();
            String modifyMemberId = null;
            if (modifyMemberNum != null) {
                modifyMemberId = memberMapper.selectMemberByMemberNum(modifyMemberNum).get().getMemberId();
            }

            boardRespond.setModifyMemberId(modifyMemberId);
            boardRespond.setCommentCount(postMapper.findCommentCountByPostNum(
                boardRespond.getPostNum()));
        }

        return boardResponds;
    }

    @Override
    public void insertPost(Post post) {
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
        if (memberMapper.selectMemberByMemberId(sessionId).get().getMemberGrade().equals(MemberGrade.ADMIN)) {
            return;
        }

        Optional<Member> writerMember = Optional.ofNullable(findWriterIdPostNum(postNum)
            .orElseThrow(() -> new MemberNotFoundException("해당 번호의 회원이 존재하지 않습니다.")));

        if (!writerMember.get().getMemberId().equals(sessionId)) {
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

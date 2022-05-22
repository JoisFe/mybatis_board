package com.nhnacademy.jdbc.board.like.service.Impl;

import com.nhnacademy.jdbc.board.like.domain.Love;
import com.nhnacademy.jdbc.board.like.mapper.LoveMapper;
import com.nhnacademy.jdbc.board.like.requestDto.LoveRequestDto;
import com.nhnacademy.jdbc.board.like.responseDto.LovePostResponseDto;
import com.nhnacademy.jdbc.board.like.service.LoveService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultLikeService implements LoveService {
    private final LoveMapper loveMapper;

    public DefaultLikeService(LoveMapper loveMapper) {
        this.loveMapper = loveMapper;
    }


    @Override
    public void doLove(LoveRequestDto loveRequestDto) {
        Love love = new Love(loveRequestDto.getMemberNum(), loveRequestDto.getPostNum());

        loveMapper.insertLove(love);
    }

    @Override
    public void unDoLove(LoveRequestDto loveRequestDto) {
        Love love = new Love(loveRequestDto.getMemberNum(), loveRequestDto.getPostNum());

        loveMapper.deleteLove(love);
    }

    @Override
    public Love findLove(LoveRequestDto loveRequestDto) {
        Love love = new Love(loveRequestDto.getMemberNum(), loveRequestDto.getPostNum());

        return loveMapper.selectLove(love);
    }

    @Override
    public List<LovePostResponseDto> getLovePosts(Long memberNum) {
        return loveMapper.selectLovePostList(memberNum);
    }

}

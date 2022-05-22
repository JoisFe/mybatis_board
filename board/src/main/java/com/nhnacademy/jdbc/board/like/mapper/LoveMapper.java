package com.nhnacademy.jdbc.board.like.mapper;

import com.nhnacademy.jdbc.board.like.domain.Love;
import com.nhnacademy.jdbc.board.like.requestDto.LoveRequestDto;
import com.nhnacademy.jdbc.board.like.responseDto.LovePostResponseDto;
import java.util.List;

public interface LoveMapper {
    void insertLove(Love love);
    void deleteLove(Love love);
    Love selectLove(Love love);
    List<LovePostResponseDto> selectLovePostList(Long memberNum);
}

package com.nhnacademy.jdbc.board.like.service;

import com.nhnacademy.jdbc.board.like.domain.Love;
import com.nhnacademy.jdbc.board.like.requestDto.LoveRequestDto;
import com.nhnacademy.jdbc.board.like.responseDto.LovePostResponseDto;
import java.util.List;

public interface LoveService {
    void doLove(LoveRequestDto loveRequestDto);
    void unDoLove(LoveRequestDto loveRequestDto);
    Love findLove(LoveRequestDto loveRequestDto);
    List<LovePostResponseDto> getLovePosts(Long memberNum);
}

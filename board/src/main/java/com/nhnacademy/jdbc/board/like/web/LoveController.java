package com.nhnacademy.jdbc.board.like.web;

import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.like.requestDto.LoveRequestDto;
import com.nhnacademy.jdbc.board.like.responseDto.LovePostResponseDto;
import com.nhnacademy.jdbc.board.like.service.LoveService;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoveController {
    private final LoveService loveService;

    public LoveController(LoveService loveService) {
        this.loveService = loveService;
    }

    @GetMapping("/like/register")
    public String doLove(@RequestParam(value = "memberNum") Long memberNum, @RequestParam(value = "postNum") Long postNum) {
        LoveRequestDto loveRequestDto = new LoveRequestDto(memberNum, postNum);

        loveService.doLove(loveRequestDto);

        return "redirect:/post/detail/" + postNum;
    }

    @GetMapping("/like/delete")
    public String unDoLove(@RequestParam(value = "memberNum") Long memberNum, @RequestParam(value = "postNum") Long postNum) {
        LoveRequestDto loveRequestDto = new LoveRequestDto(memberNum, postNum);

        loveService.unDoLove(loveRequestDto);

        return "redirect:/post/detail/" + postNum;
    }

    @GetMapping("/like/list")
    public String likeList(@RequestParam(value = "memberNum") Long memberNum, Model model) {
        List<LovePostResponseDto> posts =
            loveService.getLovePosts(memberNum);

        model.addAttribute("posts", posts);

        return "lovePostList";
    }
}

package com.nhnacademy.jdbc.board.post.web;

import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.request.PostRegisterRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.jdbc.board.post.domain.Post;
import org.springframework.web.bind.annotation.PostMapping;


@Controller("/post")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    private static final int DELETE_STATE = 1;
    private static final int NOT_DELETE_STATE = 0;

    public PostController(PostService postService,
                          MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }


    @GetMapping("/board")
    public String postList(Model model) {
        List<Post> posts = postService.getPosts(NOT_DELETE_STATE);

        model.addAttribute("posts", posts);

        return "board";
    }

    @GetMapping("/post/register")
    public String postRegister() {
        return "postRegister";
    }

    @PostMapping("/post/register")
    public String postRegister(PostRegisterRequest postRegisterRequest, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(true);
        String id = (String) httpSession.getAttribute("id");

        Long memberNum = memberService.getMemberByMemberId(id).get().getMemberNum();
        /*
        FIXME : 이거도 아이디로 멤버찾는데 없는 에러 잡아야하는거 아님 ??
         */

        Post post = new Post(
            memberNum,
            postRegisterRequest.getPostTitle(),
            postRegisterRequest.getPostContent(),
            new Date(),
            NOT_DELETE_STATE
        );

        postService.insertPost(post);

        return "redirect:/board";
    }
}

package com.nhnacademy.jdbc.board.comment.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.comment.requestDto.CommentRequestDto;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    public CommentController(CommentService commentService,
                             PostService postService, MemberService memberService) {
        this.commentService = commentService;
        this.postService = postService;
        this.memberService = memberService;
    }

//    @GetMapping("/post/detail/{postNum}")
//    public String commentList(@PathVariable("postNum") Long postNum, Model model) {
//        List<Comment> comments = commentService.getComments(postNum);
//        model.addAttribute("comments", comments);

//        return "postDetail";
//    }

//    @PostMapping("/comment/register")
//    public String commentRegister() {
//         return "commentRegister";
//         //리턴을 리다이렉트 떄리기
//    }

    @PostMapping("/comment/register/{postNum}")
    public String commentRegister(@Valid CommentRequestDto commentRegisterRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest, @PathVariable("postNum") Long postNum){
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        HttpSession httpSession = httpServletRequest.getSession(true);
        String id = (String) httpSession.getAttribute("id");

        Long memberNum = memberService.getMemberByMemberId(id)
            .orElseThrow(() -> new MemberNotFoundException("로그인 하지 않았습니다."))
            .getMemberNum();

        commentService.insertComment(commentRegisterRequest, postNum, memberNum);

        return "redirect:/post/detail/" + postNum;
    }
}

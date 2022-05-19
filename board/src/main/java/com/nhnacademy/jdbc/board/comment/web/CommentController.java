package com.nhnacademy.jdbc.board.comment.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.request.CommentRequest;
import com.nhnacademy.jdbc.request.PostRequest;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
//
//        return "postDetail";
//    }

    @PostMapping("/comment/register")
    public String commentRegister() {
         return "commentRegister";
         //리턴을 리다이렉트 떄리기
    }

    @PostMapping("/comment/register/{postNum}")
    public String commentRegister(CommentRequest commentRegisterRequest, HttpServletRequest httpServletRequest, @PathVariable("postNum") Long postNum){
        HttpSession httpSession = httpServletRequest.getSession(true);
        String id = (String) httpSession.getAttribute("id");
        Long memberNum = memberService.getMemberByMemberId(id).get().getMemberNum();

        Comment comment = new Comment(
            memberNum,
            postNum,
            commentRegisterRequest.getCommentContent(),
            new Date()
        );

        commentService.insertComment(comment);

        return "redirect:/post/detail/${postnum}";
    }
}

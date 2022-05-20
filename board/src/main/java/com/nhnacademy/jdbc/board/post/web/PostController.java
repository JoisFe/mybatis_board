package com.nhnacademy.jdbc.board.post.web;

import com.nhnacademy.jdbc.board.post.page.Page;
import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.requestDao.PostRequestDao;
import com.nhnacademy.jdbc.board.post.respondDao.BoardRespondDao;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.jdbc.board.post.domain.Post;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    private static final int DELETE_STATE = 1;
    private static final int NOT_DELETE_STATE = 0;

    public PostController(PostService postService,
                          MemberService memberService,
                          CommentService commentService) {
        this.postService = postService;
        this.memberService = memberService;
        this.commentService = commentService;
    }

    @ModelAttribute("sessionId")
    public String sessionId(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(true);

        return (String) httpSession.getAttribute("id");

    }

    @GetMapping("/board")
    public String postList(@RequestParam(value="page", defaultValue = "1") String page, Model model) {

        Page paging = new Page(postService.getPageSize(NOT_DELETE_STATE), Integer.parseInt(page));

        List<BoardRespondDao> posts = postService.getPosts(NOT_DELETE_STATE, paging.getCurrentPage());

        model.addAttribute("posts", posts);
        model.addAttribute("paging", paging);

        return "boardView";
    }

    @GetMapping("/post/register")
    public String postRegister() {
        return "postRegister";
    }

    @PostMapping("/post/register")
    public String postRegister(@ModelAttribute("sessionId") String sessionId, PostRequestDao postRegisterRequest) {
        Long memberNum = memberService.getMemberByMemberId(sessionId).get().getMemberNum();
        /*
        FIXME : 이거도 아이디로 멤버찾는데 없는 에러 잡아야하는거 아님 ??
         */

        Post post = new Post(
            memberNum,
            postRegisterRequest.getPostTitle(),
            postRegisterRequest.getPostContent(),
            new Date(),
            null,
            NOT_DELETE_STATE,
            null
        );

        postService.insertPost(post);

        return "redirect:/board";
    }

    @GetMapping("/post/detail/{postNum}")
    public String postDetail(@ModelAttribute("sessionId") String sessionId, @PathVariable("postNum") Long postNum, Model model) {
        Optional<Post> post = postService.getPostByPostNum(postNum);
        model.addAttribute("post", post.get());

        List<Comment> comments = commentService.getComments(postNum);
        model.addAttribute("comments", comments);


        //FIXME: Null처리 생각하기, Session id값과 등록한 녀석의 id가 같은지 고민하기

        model.addAttribute("memberId", memberService.getMemberByMemberId(sessionId).get().getMemberId());

        return "postDetail";
    }

    @GetMapping("/post/modify/{postNum}")
    public String postModify(@ModelAttribute("sessionId") String sessionId, @PathVariable("postNum") Long postNum, Model model) {
        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        Optional<Post> post = postService.getPostByPostNum(postNum);
        model.addAttribute("post", post.get());
        //FIXME: Null처리 생각하기, Session id값과 등록한 녀석의 id가 같은지 고민하기

        return "postModify";
    }

    @PostMapping("/post/modify/{postNum}")
    public String postModify(@ModelAttribute("sessionId") String sessionId, @PathVariable("postNum") Long postNum, PostRequestDao postRequest) {
        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        Member modifyMember = memberService.getMemberByMemberId(sessionId).get();
        postService.modifyPost(postRequest.getPostTitle(), postRequest.getPostContent(), postNum, modifyMember.getMemberNum());

        return "redirect:/board";
    }

    @GetMapping("/post/delete/{postNum}")
    public String postDelete(@ModelAttribute("sessionId") String sessionId, @PathVariable("postNum") Long postNum) {
        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        postService.deletePost(DELETE_STATE, postNum);

        return "redirect:/board";
    }
}

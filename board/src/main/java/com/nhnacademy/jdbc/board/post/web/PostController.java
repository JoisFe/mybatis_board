package com.nhnacademy.jdbc.board.post.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.exception.NotAuthorizeException;
import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.domain.MemberGrade;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.page.Page;
import com.nhnacademy.jdbc.board.post.requestDto.PostRequestDto;
import com.nhnacademy.jdbc.board.post.respondDto.BoardRespondDto;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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
    public String postList(@ModelAttribute("sessionId") String sessionId,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           Model model) {

        Page paging = new Page(postService.getPageSize(NOT_DELETE_STATE), Integer.parseInt(page));

        List<BoardRespondDto> posts =
            postService.getPosts(NOT_DELETE_STATE, paging.getCurrentPage());

        if (sessionId != null) {
            model.addAttribute("sessionId", sessionId);
            model.addAttribute("sessionGrade",
                memberService.getMemberByMemberId(sessionId)
                    .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."))
                    .getMemberGrade());
        }

        model.addAttribute("posts", posts);
        model.addAttribute("paging", paging);

        return "boardView";
    }

    @GetMapping("/post/register")
    public String postRegister(@ModelAttribute("sessionId") String sessionId) {
        if (sessionId == null) {
            throw new MemberNotFoundException("로그인을 하지 않았습니다.");
        }
        return "postRegister";
    }

    @PostMapping("/post/register")
    public String postRegister(@ModelAttribute("sessionId") String sessionId,
                               @Valid @ModelAttribute PostRequestDto postRegisterRequest,
                               BindingResult bindingResult,
                               @RequestParam("multipartFile") MultipartFile multipartFile) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long memberNum = memberService.getMemberByMemberId(sessionId)
            .orElseThrow(() -> new MemberNotFoundException("로그인을 하지 않았습니다."))
            .getMemberNum();

        postService.insertPost(postRegisterRequest, memberNum, multipartFile);

        return "redirect:/board";
    }

    @GetMapping("/post/detail/{postNum}")
    public String postDetail(@ModelAttribute("sessionId") String sessionId,
                             @PathVariable("postNum") Long postNum, Model model) {
        Optional<Post> post = postService.getPostByPostNum(postNum);
        model.addAttribute("post", post
            .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다.")));

        List<Comment> comments = commentService.getComments(postNum);
        model.addAttribute("comments", comments);

        if (sessionId != null) {
            model.addAttribute("memberId",
                    memberService.getMemberByMemberId(sessionId)
                        .orElseThrow(() -> new MemberNotFoundException("로그인 하지 않았습니다."))
                        .getMemberId());
        }

        return "postDetail";
    }

    @GetMapping("/post/modify/{postNum}")
    public String postModify(@ModelAttribute("sessionId") String sessionId,
                             @PathVariable("postNum") Long postNum, Model model) {
        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        model.addAttribute("post", postService.getPostByPostNum(postNum)
            .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다.")));

        return "postModify";
    }

    @PostMapping("/post/modify/{postNum}")
    public String postModify(@ModelAttribute("sessionId") String sessionId,
                             @PathVariable("postNum") Long postNum,
                             @Valid PostRequestDto postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        if (sessionId != null) {
            Member modifyMember =
                memberService.getMemberByMemberId(sessionId)
                    .orElseThrow(() -> new MemberNotFoundException("로그인을 하지 않았습니다."));

            postService.modifyPost(postRequest.getPostTitle(), postRequest.getPostContent(),
                postNum, modifyMember.getMemberNum());
        }
        return "redirect:/board";
    }

    @GetMapping("/post/delete/{postNum}")
    public String postDelete(@ModelAttribute("sessionId") String sessionId,
                             @PathVariable("postNum") Long postNum) {
        postService.matchCheckSessionIdAndWriterId(postNum, sessionId);

        postService.deletePost(DELETE_STATE, postNum);

        return "redirect:/board";
    }

    @GetMapping("/post/deleteRestore")
    public String postDeleteRestore(@ModelAttribute("sessionId") String sessionId, @RequestParam(value = "page", defaultValue = "1") String page,
                                    Model model) {
        MemberGrade memberGrade = memberService.getMemberByMemberId(sessionId)
            .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."))
            .getMemberGrade();

        if (memberGrade.equals(MemberGrade.USER)) {
            throw new NotAuthorizeException("관리자 권한이 아니므로 접근 불가합니다.");
        }

        Page paging = new Page(postService.getPageSize(DELETE_STATE), Integer.parseInt(page));

        List<BoardRespondDto> posts =
            postService.getPosts(DELETE_STATE, paging.getCurrentPage());


        model.addAttribute("posts", posts);
        model.addAttribute("paging", paging);

        return "adminPostRestore";
    }

    @GetMapping("/post/restore/{postNum}")
    public String postRestore(@ModelAttribute("sessionId") String sessionId,
                              @PathVariable("postNum") Long postNum) {
        postService.restorePostByPostNum(postNum, sessionId);

        return "redirect:/board";
    }

    @PostMapping("/board/search")
    public String postSearch(@RequestParam("searchValue") String searchValue, Model model) {

        model.addAttribute("searchValue", searchValue);

        return "redirect:/board/search?searchValue=" + searchValue;
    }

    @GetMapping("/board/search/{searchValue}")
    public String postSearch(@PathVariable("searchValue") String searchValue, Model model
        ,@RequestParam(value = "page", defaultValue = "1") String page,
                             @ModelAttribute("sessionId") String sessionId) {

        Page paging = new Page(postService.getPageSize(NOT_DELETE_STATE), Integer.parseInt(page));

        List<BoardRespondDto> posts =
            postService.getPostsWithSearch(NOT_DELETE_STATE, paging.getCurrentPage(), searchValue);

        if (sessionId != null) {
            model.addAttribute("sessionId", sessionId);
            model.addAttribute("sessionGrade",
                memberService.getMemberByMemberId(sessionId)
                    .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."))
                    .getMemberGrade());
        }

        model.addAttribute("postsSearch", posts);
        model.addAttribute("pagingSearch", paging);

        return "boardViewSearch";
    }

}

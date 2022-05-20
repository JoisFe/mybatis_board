package com.nhnacademy.jdbc.board.member.web;

import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.member.request.LoginRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "loginForm";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest httpServletRequest, Model model) {
        if (memberService.matches(loginRequest.getMemberId(), loginRequest.getMemberPwd())) {
            HttpSession httpSession = httpServletRequest.getSession(true);
            httpSession.setAttribute("id", loginRequest.getMemberId());
            System.out.println("아이디입니다." + httpSession.getAttribute("id"));
            model.addAttribute("id", loginRequest.getMemberId());
            return "redirect:/board";
        }
        return "redirect:/login";
    }

}

package com.nhnacademy.jdbc.board.member.web;

import com.nhnacademy.jdbc.board.member.domain.Member;
import com.nhnacademy.jdbc.board.member.service.MemberService;
import com.nhnacademy.jdbc.board.student.domain.Student;
import com.nhnacademy.jdbc.board.student.service.StudentService;
import com.nhnacademy.jdbc.board.student.service.impl.DefaultStudentService;
import com.nhnacademy.jdbc.request.LoginRequest;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String login(LoginRequest loginRequest, HttpServletRequest httpServletRequest, Model model) {
        if (memberService.matches(loginRequest.getMemberId(), loginRequest.getMemberPwd())) {
            HttpSession httpSession = httpServletRequest.getSession(true);

            httpSession.setAttribute("id", loginRequest.getMemberId());

            model.addAttribute("id", loginRequest.getMemberId());
            return "loginSuccess";
        }

        return "redirect:/login";
    }

}

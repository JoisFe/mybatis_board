package com.nhnacademy.jdbc.board.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginCheckInterceptor implements HandlerInterceptor {

    String blackList = "/login\n/loginForm.html\n/board\n/post/detail\n/boardView.html\n/postDetail.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        request.setAttribute("loginCheck", false);

       if (Objects.nonNull(session)) {
            request.setAttribute("loginCheck", true);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String[] arr = blackList.split("\n");
        List<String> uris = Arrays.stream(arr).map(String::trim).collect(Collectors.toList());/**/

        if (!uris.contains(request.getRequestURI())) {

            if (!(boolean) request.getAttribute("loginCheck")) {
                response.sendRedirect("/login");
            }
        }
    }
}

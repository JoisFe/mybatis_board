package com.nhnacademy.jdbc.board.web;

import com.nhnacademy.jdbc.board.exception.CommentNotFouncException;
import com.nhnacademy.jdbc.board.exception.MemberNotFoundException;
import com.nhnacademy.jdbc.board.exception.NotAuthorizeException;
import com.nhnacademy.jdbc.board.exception.NotMatchMemberIdException;
import com.nhnacademy.jdbc.board.exception.PostFileUploadException;
import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler({CommentNotFouncException.class, MemberNotFoundException.class,
        NotMatchMemberIdException.class, PostNotFoundException.class,
        ValidationFailedException.class, PostFileUploadException.class,
        NotAuthorizeException.class})
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("exception", ex);
        return "error";
    }

}

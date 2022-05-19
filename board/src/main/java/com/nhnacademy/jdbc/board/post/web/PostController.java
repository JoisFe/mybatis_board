package com.nhnacademy.jdbc.board.post.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/course")
    public String courseList(Model model) {
        List<Course> courses = courseCreationService.getCourseList();

        model.addAttribute("courses", courses);
        model.addAttribute("teacherService", teacherService);
        model.addAttribute("subjectService", subjectService);

        return "courseList";
    }
}

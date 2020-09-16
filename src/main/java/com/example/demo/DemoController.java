package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private PostService service;

    @RequestMapping("/posts/top")
    public List<PostDTO> topPost() {
        List<Post> posts = this.service.getPostsByTopComments();
        List<PostDTO> response = new ArrayList<>();
        for (Post p : posts) {
            response.add(new PostDTO(p));
        }
        return response;
    }

    @RequestMapping("/comments/search")
    public List<Comment> search(@RequestParam(required = false) String name,
            @RequestParam(required = false) String body, @RequestParam(required = false) String email) {
        return this.service.searchComments(name, body, email);
    }
}

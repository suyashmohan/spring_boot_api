package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    private RestTemplate restTemplate;

    @Autowired
    public PostService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Comment[] getComments() {
        String url = "https://jsonplaceholder.typicode.com/comments";
        return this.restTemplate.getForObject(url, Comment[].class);
    }

    public Post[] getPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        return this.restTemplate.getForObject(url, Post[].class);
    }

    public List<Post> getPostsByTopComments() {
        List<Comment> comments = Arrays.asList(getComments());
        List<Post> posts = Arrays.asList(getPosts());
        HashMap<Integer, Post> postsMap = new HashMap<>();

        for(Post p: posts) {
            postsMap.put(p.getId(), p);
        }

        for(Comment c: comments) {
            Post p = postsMap.get(c.getPostId());
            p.setCommentCount(p.getCommentCount() + 1);
        }

        Collections.sort(posts);

        return posts;
    }

    public List<Comment> searchComments(String name, String body, String email) {
        List<Comment> comments = Arrays.asList(getComments());
        List<Comment> result = new ArrayList<>();
        for(Comment c: comments) {
            boolean check = false;
            if (name !=  null){
                check  = c.getName().contains(name);
            }
            if(body != null && !check) {
                check  = c.getBody().contains(body);
            }
            if(email != null && !check) {
                check  = c.getEmail().contains(email);
            }

            if (check) {
                result.add(c);
            }
        }
        return result;
    }
}

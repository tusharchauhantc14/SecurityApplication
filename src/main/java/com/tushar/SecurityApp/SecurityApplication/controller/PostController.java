package com.tushar.SecurityApp.SecurityApplication.controller;

import com.tushar.SecurityApp.SecurityApplication.dto.PostDTO;
import com.tushar.SecurityApp.SecurityApplication.services.PostService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<PostDTO> getAllPost(){
        return postService.getAllPosts();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @GetMapping("/{postId}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN') OR hasAnyAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public PostDTO updatePostById(@PathVariable Long postId, @RequestBody PostDTO inputPost){
        return postService.updatePostById(postId,inputPost);
    }
}

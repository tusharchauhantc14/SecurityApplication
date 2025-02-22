package com.tushar.SecurityApp.SecurityApplication.services.utils;

import com.tushar.SecurityApp.SecurityApplication.dto.PostDTO;
import com.tushar.SecurityApp.SecurityApplication.entities.PostEntity;
import com.tushar.SecurityApp.SecurityApplication.entities.User;
import com.tushar.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;
    public boolean isOwnerOfPost(Long postId){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post= postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }

}

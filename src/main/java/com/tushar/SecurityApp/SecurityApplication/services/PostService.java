package com.tushar.SecurityApp.SecurityApplication.services;


import com.tushar.SecurityApp.SecurityApplication.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePostById(Long postId, PostDTO inputPost);
}

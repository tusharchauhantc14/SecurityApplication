package com.tushar.SecurityApp.SecurityApplication.services;

import com.tushar.SecurityApp.SecurityApplication.dto.PostDTO;
import com.tushar.SecurityApp.SecurityApplication.entities.PostEntity;
import com.tushar.SecurityApp.SecurityApplication.entities.User;
import com.tushar.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.tushar.SecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity),PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity= postRepository
                .findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not present with id: "+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(Long postId, PostDTO inputPost) {
       PostEntity olderPost= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not present with id: "+postId));
       inputPost.setId(postId);
       modelMapper.map(inputPost,olderPost);
       PostEntity savedPostEntity= postRepository.save(olderPost);
       return modelMapper.map(savedPostEntity, PostDTO.class);
    }
}

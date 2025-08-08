package com.sanawulai.blogapplication.service;

import com.sanawulai.blogapplication.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
}

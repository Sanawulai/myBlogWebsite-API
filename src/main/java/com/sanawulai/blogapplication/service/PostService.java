package com.sanawulai.blogapplication.service;

import com.sanawulai.blogapplication.payload.PostDto;
import com.sanawulai.blogapplication.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    //delete post by id
    void deletePostById(long id);


}

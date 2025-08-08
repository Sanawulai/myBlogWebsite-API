package com.sanawulai.blogapplication.service.impl;

import com.sanawulai.blogapplication.entity.Post;
import com.sanawulai.blogapplication.payload.PostDto;
import com.sanawulai.blogapplication.repository.PostRepository;
import com.sanawulai.blogapplication.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){

        //create dto to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());


        Post saveNewPost = postRepository.save(post);


        //convert entity to DTO
        PostDto postResponse = new PostDto();
        postResponse.setTitle(saveNewPost.getTitle());
        postResponse.setId(saveNewPost.getId());
        postResponse.setContent(saveNewPost.getContent());
        postResponse.setDescription(saveNewPost.getDescription());
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return List.of();
    }


}

package com.sanawulai.blogapplication.service.impl;

import com.sanawulai.blogapplication.entity.Post;
import com.sanawulai.blogapplication.payload.PostDto;
import com.sanawulai.blogapplication.repository.PostRepository;
import com.sanawulai.blogapplication.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){

        //create dto to entity
       Post post = mapToEntity(postDto);
        Post NewPost = postRepository.save(post);


        //convert entity to DTO
        PostDto postResponse = mapToDTO(NewPost);
        return postResponse;

    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post ->
                mapToDTO(post)).collect(Collectors.toList());
    }

    //convert entity to dto
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    //convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }


}

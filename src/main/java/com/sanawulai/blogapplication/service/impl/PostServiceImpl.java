package com.sanawulai.blogapplication.service.impl;

import com.sanawulai.blogapplication.entity.Category;
import com.sanawulai.blogapplication.entity.Post;
import com.sanawulai.blogapplication.exception.ResourceNotFoundException;
import com.sanawulai.blogapplication.payload.PostDto;
import com.sanawulai.blogapplication.payload.PostResponse;
import com.sanawulai.blogapplication.repository.CategoryRepository;
import com.sanawulai.blogapplication.repository.PostRepository;
import com.sanawulai.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;


    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository)
    {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        //create dto to entity
       Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post NewPost = postRepository.save(post);


        //convert entity to DTO
        PostDto postResponse = mapToDTO(NewPost);
        return postResponse;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {



        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page <Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List <PostDto> content = listOfPosts.stream().map(post ->
                mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", id));

        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from the db
        Post post = postRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        //save the update into the db
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", id));

        post.setTitle(post.getTitle());
        post.setDescription(post.getDescription());
        post.setContent(post.getContent());

        postRepository.delete(post);
    }

    //convert entity to dto
    private PostDto mapToDTO(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);


//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
        return postDto;
    }

    //convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }


}

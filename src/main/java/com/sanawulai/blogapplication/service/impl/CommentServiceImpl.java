package com.sanawulai.blogapplication.service.impl;

import ch.qos.logback.classic.Logger;
import com.sanawulai.blogapplication.entity.Comment;
import com.sanawulai.blogapplication.entity.Post;
import com.sanawulai.blogapplication.exception.BlogAPIException;
import com.sanawulai.blogapplication.exception.ResourceNotFoundException;
import com.sanawulai.blogapplication.payload.CommentDto;
import com.sanawulai.blogapplication.repository.CommentRepository;
import com.sanawulai.blogapplication.repository.PostRepository;
import com.sanawulai.blogapplication.service.CommentService;
import com.sanawulai.blogapplication.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    //you can add @Autoweidred here
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to db
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).toList();

    }

    @Override
    public CommentDto getCommentsById(long postId, long commentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment", "id", commentId));



        if (!Objects.equals(
                comment.getPost() != null ? comment.getPost().getId() : null,
                post.getId()
        )) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        } else {
            return mapToDTO(comment);
        }


    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!Objects.equals(
                comment.getPost() != null ? comment.getPost().getId() : null,
                post.getId()
        )) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);



    }

    @Override
    public void deleteComment(long postId, long commentId) {

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));


        //retrieve comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!Objects.equals(
                comment.getPost() != null ? comment.getPost().getId() : null,
                post.getId()
        )) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);

    }

    private CommentDto mapToDTO(Comment comment){
        CommentDto CommentDto = new CommentDto();
        CommentDto.setId(comment.getId());
        CommentDto.setName(comment.getName());
        CommentDto.setEmail(comment.getEmail());
        CommentDto.setBody(comment.getBody());

        return CommentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}

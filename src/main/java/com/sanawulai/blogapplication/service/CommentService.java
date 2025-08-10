package com.sanawulai.blogapplication.service;

import com.sanawulai.blogapplication.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsById(long postId,long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);

    void deleteComment(long postId, long commentId);
}

package com.sanawulai.blogapplication.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    private String title;

    private String content;

    private String description;

    private Set<CommentDto> comments;
}

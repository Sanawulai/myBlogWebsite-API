package com.sanawulai.blogapplication.repository;

import com.sanawulai.blogapplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long>{
}

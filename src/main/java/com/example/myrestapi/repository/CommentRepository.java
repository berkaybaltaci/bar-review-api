package com.example.myrestapi.repository;

import com.example.myrestapi.entity.Comment;
import com.example.myrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long id);
}
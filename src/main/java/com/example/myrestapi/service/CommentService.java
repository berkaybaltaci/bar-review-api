package com.example.myrestapi.service;

import com.example.myrestapi.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();
    Comment getComment(Long id);
}

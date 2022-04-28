package com.example.myrestapi.comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();

    Comment getComment(Long id);

    Comment addComment(Comment comment);

    void deleteComment(Long id);

}

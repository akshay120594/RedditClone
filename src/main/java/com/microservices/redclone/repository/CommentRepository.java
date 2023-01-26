package com.microservices.redclone.repository;

import com.microservices.redclone.modal.Comment;
import com.microservices.redclone.modal.Post;
import com.microservices.redclone.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}

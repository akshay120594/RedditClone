package com.microservices.redclone.repository;

import com.microservices.redclone.modal.Post;
import com.microservices.redclone.modal.Subreddit;
import com.microservices.redclone.modal.User;
import org.hibernate.dialect.PostgreSQL94Dialect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}

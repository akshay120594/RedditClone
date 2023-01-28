package com.microservices.redclone.repository;

import com.microservices.redclone.modal.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(String subredditName);
}

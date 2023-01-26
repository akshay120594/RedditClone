package com.microservices.redclone.repository;

import com.microservices.redclone.modal.Post;
import com.microservices.redclone.modal.User;
import com.microservices.redclone.modal.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}

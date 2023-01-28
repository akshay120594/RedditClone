package com.microservices.redclone.service;

import com.microservices.redclone.dto.PostRequest;
import com.microservices.redclone.dto.PostResponse;
import com.microservices.redclone.exceptions.PostNotFoundException;
import com.microservices.redclone.exceptions.SubredditNotFoundException;
import com.microservices.redclone.mapper.PostMapper;
import com.microservices.redclone.modal.Post;
import com.microservices.redclone.modal.Subreddit;
import com.microservices.redclone.modal.User;
import com.microservices.redclone.repository.PostRepository;
import com.microservices.redclone.repository.SubredditRepository;
import com.microservices.redclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
       Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("post not found for id"+id.toString()));
       return postMapper.mapToDto(post);
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public void save(PostRequest postRequest){
        Subreddit subreddit =subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(()->new SubredditNotFoundException("invalid subreddit"));

        postRepository.save(postMapper.map(postRequest,subreddit,authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}

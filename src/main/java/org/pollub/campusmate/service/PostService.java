package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Post;
import org.pollub.campusmate.exception.PostNotFound;
import org.pollub.campusmate.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    //TODO: exception handling

    private final PostRepository postRepository;

    public Post getPost(long postId){

        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("Post with id " + postId + " not found"));
    }

    public Post addPost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(long postId){

        if(!postRepository.existsById(postId)){
            throw new PostNotFound("Cannot execute delete operation. Post with id " + postId + " not found");
        }
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts(){
        Iterable<Post> posts = postRepository.findAll();
        List<Post> foundPosts = new ArrayList<>();

        posts.forEach(foundPosts::add);

        if(foundPosts.isEmpty()){
            throw new PostNotFound("No posts found");
        }

        return foundPosts;
    }

    //TODO: edit post method
}
package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Post;
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
        return postRepository.findById(postId).orElseThrow(null);
    }

    public Post addPost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(long postId){

//        if(!postRepository.existsById(postId)){
//
//        }
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts(){
        Iterable<Post> posts = postRepository.findAll();
        List<Post> foundPosts = new ArrayList<>();

        posts.forEach(foundPosts::add);

        if(foundPosts.isEmpty()){
            return null;
        }

        return foundPosts;
    }

    //TODO: edit post method
}
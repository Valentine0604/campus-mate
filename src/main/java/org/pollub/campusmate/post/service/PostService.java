package org.pollub.campusmate.post.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.entity.Post;
import org.pollub.campusmate.post.exception.PostNotFound;
import org.pollub.campusmate.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getPost(Long postId){

        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("Post with id " + postId + " not found"));
    }

    public void addPost(Post post){
        postRepository.save(post);
    }

    public void editPost(Long postId, PostCreationDto post){
        if(!postRepository.existsById(postId)){
            throw new PostNotFound("Cannot execute update operation. Post with id " + postId + " not found");
        }

        Post postToEdit = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("Post with id " + postId + " not found"));


        if(post.getPostTitle() != null){
            postToEdit.setPostTitle(post.getPostTitle());
        }

        if(post.getPostContent() != null){
            postToEdit.setPostContent(post.getPostContent());
        }

        postRepository.save(postToEdit);

    }

    public void deletePost(Long postId){

        if(!postRepository.existsById(postId)){
            throw new PostNotFound("Cannot execute delete operation. Post with id " + postId + " not found");
        }
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts(){
        List<Post> foundPosts = (List<Post>)postRepository.findAll();

        if(foundPosts.isEmpty()){
            throw new PostNotFound("No posts found");
        }

        return foundPosts;
    }
}
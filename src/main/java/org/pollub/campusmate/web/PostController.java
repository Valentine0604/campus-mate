package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Post;
import org.pollub.campusmate.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPosts(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        postService.addPost(post);
        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }
}

package org.pollub.campusmate.web;

import aj.org.objectweb.asm.commons.Remapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.PostCreationDTO;
import org.pollub.campusmate.dto.PostDTO;
import org.pollub.campusmate.entity.Post;
import org.pollub.campusmate.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPosts(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        PostDTO postDTO = new PostDTO(post.getPostTitle(), post.getPostContent(), post.getUser().getEmail(), post.getCreatedAt(), post.getUpdatedAt());
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }


    //TODO: add current user as author
    //Adding teams in front by clicking add next to the team list in post
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostCreationDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        postService.addPost(post);
        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postsDTO = new ArrayList<>();
        posts.forEach(post -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostTitle(post.getPostTitle());
            postDTO.setPostContent(post.getPostContent());
            if (post.getUser() != null){
                postDTO.setAuthor(post.getUser().getEmail());
            } else {
                postDTO.setAuthor("Anonymous");
            }
            postDTO.setCreatedAt(post.getCreatedAt());
            postDTO.setUpdatedAt(post.getUpdatedAt());
            postsDTO.add(postDTO);
        }
                );
        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }
}

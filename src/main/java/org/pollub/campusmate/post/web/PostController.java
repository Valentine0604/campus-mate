package org.pollub.campusmate.post.web;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.post.mapper.PostCreationMapper;
import org.pollub.campusmate.post.mapper.PostMapper;
import org.pollub.campusmate.post.service.PostService;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostCreationMapper postCreationMapper;
    private final UserService userService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        var post = postService.getPost(postId);
        String authorName = "Anonymous";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && post.getUser() != null) {
            authorName = post.getUser().getFirstName() + " " + post.getUser().getLastName();
        }

        var postDto = postMapper.toDto(post);
        postDto.setAuthor(authorName);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> createPost(@RequestBody PostCreationDto postCreationDto) {
        var post = postCreationMapper.toEntity(postCreationDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var email = authentication.getName();
            var currentUser = userService.getUserByEmail(email);

            if (currentUser != null) {
                post.setUser(currentUser);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Dodanie posta do bazy
        postService.addPost(post);
        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }



    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, @RequestBody PostCreationDto postCreationDto) {
        postService.editPost(postId, postCreationDto);
        return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        var posts = postService.getAllPosts();
        var postDtos = posts.stream()
                .map(post -> {
                    var postDto = postMapper.toDto(post);
                    postDto.setAuthor(post.getUser() != null ? post.getUser().getEmail() : "Anonymous");
                    return postDto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
}

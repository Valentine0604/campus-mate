package org.pollub.campusmate.post.web;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.post.entity.Post;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.post.service.PostService;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorName = "anonymous"; // Domyślnie ustawiamy "anonymous" jako autora

        if (authentication != null && authentication.isAuthenticated()) {
            String currentUsername = authentication.getName();

            // Jeśli użytkownik jest zalogowany, przypisz nazwisko autora
            Post post = postService.getPost(postId);
            if (post.getUser() != null) {
                authorName = post.getUser().getFirstName() + " " + post.getUser().getLastName();
            } else {
                // Jeśli nie ma użytkownika powiązanego z postem, pozostaw "anonymous"
                authorName = "anonymous";
            }
        } else {
            // Jeżeli nie ma autoryzacji, pozostawiamy autora jako "anonymous"
            Post post = postService.getPost(postId);
            if (post.getUser() != null) {
                authorName = post.getUser().getFirstName() + " " + post.getUser().getLastName();
            }
        }

        // Ostateczna konstrukcja DTO
        Post post = postService.getPost(postId);  // ponownie pobieramy post, by mieć dostęp do niego

        PostDto postDTO = new PostDto(
                post.getPostId(),
                post.getPostTitle(),
                post.getPostContent(),
                authorName,  // nazwisko autora
                post.getCreatedAt(),
                post.getUpdatedAt());

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }



    //TODO: add current user as author
    //Adding teams in front by clicking add next to the team list in post
    @PostMapping
    @Transactional
    public ResponseEntity<String> createPost(@RequestBody PostCreationDto postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        postService.addPost(post);
        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, @RequestBody PostCreationDto postDTO) {
        postService.editPost(postId, postDTO);
        return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postsDTO = new ArrayList<>();
        posts.forEach(post -> {
            PostDto postDTO = new PostDto();
            postDTO.setPostId(post.getPostId());
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

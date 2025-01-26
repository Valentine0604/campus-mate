package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.entity.Post;
import org.pollub.campusmate.post.repository.PostRepository;
import org.pollub.campusmate.post.service.PostService;
import org.pollub.campusmate.team.repository.TeamRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PostService postService;

    private Post testPost;
    private PostCreationDto testPostCreationDto;

    @BeforeEach
    void setUp() {
        testPost = new Post();
        testPost.setPostId(1L);
        testPost.setPostTitle("Test Post");
        testPost.setPostContent("Test Content");
        testPost.setCreatedAt(LocalDateTime.now());

        testPostCreationDto = new PostCreationDto(
                "Updated Title",
                "Updated Content",
                List.of(1L)
        );
    }

    @Test
    void getPost_ShouldReturnPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        Post result = postService.getPost(1L);

        assertNotNull(result);
        assertEquals("Test Post", result.getPostTitle());
    }

    @Test
    void addPost_ShouldSavePost() {
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        postService.addPost(testPost);

        verify(postRepository).save(testPost);
    }

    @Test
    void editPost_ShouldUpdatePost() {
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        postService.editPost(1L, testPostCreationDto);

        verify(postRepository).save(any(Post.class));
        assertEquals("Updated Title", testPost.getPostTitle());
        assertEquals("Updated Content", testPost.getPostContent());
    }

    @Test
    void getAllPosts_ShouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(testPost));

        List<Post> result = postService.getAllPosts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
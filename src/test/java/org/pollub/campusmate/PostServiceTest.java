package org.pollub.campusmate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.entity.Post;
import org.pollub.campusmate.repository.PostRepository;
import org.pollub.campusmate.service.PostService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void PostService_CreatePost_ReturnPost() {
        // Arrange
        Post post = Post.builder()
                .postId(1L)
                .postTitle("title")
                .postContent("content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        // Act
        Post savedPost = postService.addPost(post);

        // Assert
        assertEquals(1L, savedPost.getPostId(), "Post ID should be 1");
        assertEquals("title", savedPost.getPostTitle(), "Post title should be 'title'");
        assertEquals("content", savedPost.getPostContent(), "Post content should be 'content'");
    }
}

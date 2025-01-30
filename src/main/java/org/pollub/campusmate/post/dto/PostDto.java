package org.pollub.campusmate.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostDto {

    private Long postId;
    private String postTitle;
    private String postContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

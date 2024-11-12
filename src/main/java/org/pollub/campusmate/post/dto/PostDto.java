package org.pollub.campusmate.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostDto {

    private String postTitle;
    private String postContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

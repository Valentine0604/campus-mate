package org.pollub.campusmate.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostCreationDto {
    private final String postTitle;
    private final String postContent;
    private final List<Long> teams;
}

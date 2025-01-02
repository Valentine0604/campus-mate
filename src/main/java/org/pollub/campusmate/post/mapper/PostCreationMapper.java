package org.pollub.campusmate.post.mapper;

import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostCreationMapper {

    public PostCreationDto toDto(Post post) {

        if(post == null) return null;

        return new PostCreationDto(
                post.getPostTitle(),
                post.getPostContent(),
                post.getTeams()
        );
    }

    public Post toEntity(PostCreationDto postCreationDto) {
        if(postCreationDto == null) return null;

        Post post = new Post();

        post.setPostTitle(postCreationDto.getPostTitle());
        post.setPostContent(postCreationDto.getPostContent());
        post.setTeams(postCreationDto.getTeams());

        return post;
    }


}

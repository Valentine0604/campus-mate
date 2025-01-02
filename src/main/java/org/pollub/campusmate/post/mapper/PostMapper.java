package org.pollub.campusmate.post.mapper;

import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto toDto(Post post){
        if(post == null) return null;

        return new PostDto(
                post.getPostId(),
                post.getPostTitle(),
                post.getPostContent(),
                post.getUser().getEmail(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public Post toEntity(PostDto dto){
        if(dto == null) return null;

        Post post = new Post();

        post.setPostId(dto.getPostId());
        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());

        return post;
    }
}

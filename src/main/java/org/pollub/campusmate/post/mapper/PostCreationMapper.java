package org.pollub.campusmate.post.mapper;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.entity.Post;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.team.service.TeamService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Component
public class PostCreationMapper {
    private final TeamService teamService;

    public PostCreationDto toDto(Post post) {

        if(post == null) return null;

        return new PostCreationDto(
                post.getPostTitle(),
                post.getPostContent(),
                post.getTeams().stream().map(Team::getTeamId).toList()
        );
    }

    public Post toEntity(PostCreationDto postCreationDto) {
        if(postCreationDto == null) return null;

        Post post = new Post();
        post.setPostTitle(postCreationDto.getPostTitle());
        post.setPostContent(postCreationDto.getPostContent());

        Set<Team> teams = new HashSet<>();
        for (Long teamId : postCreationDto.getTeams()) {
            Team team = teamService.getTeam(teamId);
            team.addPost(post);
            teams.add(team);
        }
        post.setTeams(teams);

        return post;
    }
}

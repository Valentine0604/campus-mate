package org.pollub.campusmate.team.mapper;

import org.pollub.campusmate.team.dto.TeamDto;
import org.pollub.campusmate.team.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public TeamDto toDto(Team team){
        if(team == null) return null;


        return new TeamDto(
                team.getTeamName(),
                team.getDescription()
        );
    }

    public Team toEntity(TeamDto teamDto){
        if(teamDto == null) return null;


        Team team = new Team();
        team.setTeamName(teamDto.getTeamName());
        team.setDescription(teamDto.getDescription());

        return team;
    }
}

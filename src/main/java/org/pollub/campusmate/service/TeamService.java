package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Team;
import org.pollub.campusmate.exception.TeamNotFound;
import org.pollub.campusmate.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {

    //TODO: exception handling

    private final TeamRepository teamRepository;

    public Team getTeam(long teamId){

        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFound("Team with id " + teamId + " not found"));
    }

    public void addTeam(Team team){
        teamRepository.save(team);
    }

    public void deleteTeam(long teamId){

        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFound("Cannot execute delete operation. Team with id " + teamId + " not found");
        }

        teamRepository.deleteById(teamId);
    }

    //TODO: update team method

    public List<Team> getAllTeams(){
        Iterable<Team> teams = teamRepository.findAll();
        List<Team> foundTeams = new ArrayList<>();

        teams.forEach(foundTeams::add);

        if(foundTeams.isEmpty()){
            throw new TeamNotFound("No teams found");
        }

        return foundTeams;
    }


}

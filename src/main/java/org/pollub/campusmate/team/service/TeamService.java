package org.pollub.campusmate.team.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.team.exception.TeamNotFound;
import org.pollub.campusmate.team.repository.TeamRepository;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team getTeam(long teamId){

        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFound("Team with id " + teamId + " not found"));
    }

    public Team addTeam(Team team){

        return teamRepository.save(team);
    }

    public void deleteTeam(long teamId){

        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFound("Cannot execute delete operation. Team with id " + teamId + " not found");
        }
        Team team = teamRepository.findById(teamId).get();
        team.getUsers().clear();
        team.getPosts().clear();
        team.getEvents().clear();
        team.setCreatorId(null);
        teamRepository.save(team);
        teamRepository.deleteById(teamId);
    }

    public void updateTeam(Long teamId, Team team) {
        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFound("Cannot execute update operation. Team with id " + teamId + " not found");
        }
        Team foundTeam = teamRepository.findById(teamId).get();
        team.setTeamId(foundTeam.getTeamId());
        team.setEvents(foundTeam.getEvents());
        team.setPosts(foundTeam.getPosts());
        teamRepository.save(team);

    }

    public List<Team> getAllTeams(){
        List<Team> foundTeams = (List<Team>) teamRepository.findAll();

        if (foundTeams.isEmpty()) {
            throw new TeamNotFound("Teams not found");
        }
        return foundTeams;
    }


    public void addTeamUser(Long teamId, Long userId) {

        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFound("Team with id " + teamId + " not found");
        }
        Team foundTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFound("Team with id " + teamId + " not found"));

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User with id " + userId + " not found"));

        foundTeam.getUsers().add(foundUser);
        teamRepository.save(foundTeam);

    }

    public void removeUserFromTeam(Long teamId, Long userId) {

        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFound("Team with id " + teamId + " not found");
        }
        Team foundTeam = teamRepository.findById(teamId).get();
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User with id " + userId + " not found"));

        foundTeam.getUsers().remove(foundUser);
        teamRepository.save(foundTeam);
    }

    public Collection<Team> getTeamsByCreatorId(Long creatorId) {
        return teamRepository.findAllByCreatorId(creatorId);
    }
}

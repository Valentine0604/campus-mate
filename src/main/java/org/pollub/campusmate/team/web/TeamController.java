package org.pollub.campusmate.team.web;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.team.dto.TeamDto;
import org.pollub.campusmate.team.service.TeamService;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final ModelMapper modelMapper;
    TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long teamId) {
        Team team = teamService.getTeam(teamId);
        TeamDto teamDTO = modelMapper.map(team, TeamDto.class);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody TeamDto teamDTO) {
        teamService.addTeam(modelMapper.map(teamDTO, Team.class));
        return new ResponseEntity<>("Team created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{teamId}/addUser/{userId}")
    public ResponseEntity<String> addTeamUser(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.addTeamUser(teamId, userId);
        return new ResponseEntity<>("User added to team successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>("Team deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<String> updateTeam(@PathVariable Long teamId, @RequestBody TeamDto teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        teamService.updateTeam(teamId, team);
        return new ResponseEntity<>("Team updated successfully",HttpStatus.OK);
    }

    @GetMapping("users/{teamId}")
    public ResponseEntity<List<UserDto>> getTeamUsers(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getUsers().stream().map(user -> modelMapper.map(user, UserDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("posts/{teamId}")
    public ResponseEntity<List<PostDto>> getTeamPosts(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getPosts().stream().map(post -> modelMapper.map(post, PostDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("events/{teamId}")
    public ResponseEntity<List<EventDto>> getTeamEvents(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getEvents().stream().map(event -> modelMapper.map(event, EventDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams().stream().map(team -> modelMapper.map(team, TeamDto.class)).toList(), HttpStatus.OK);
    }

    //TODO: add delete user from team and teams by userId
}

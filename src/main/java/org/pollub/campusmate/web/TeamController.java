package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.EventDTO;
import org.pollub.campusmate.dto.PostDTO;
import org.pollub.campusmate.dto.TeamDTO;
import org.pollub.campusmate.dto.UserDTO;
import org.pollub.campusmate.entity.Team;
import org.pollub.campusmate.service.TeamService;
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
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long teamId) {
        Team team = teamService.getTeam(teamId);
        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody TeamDTO teamDTO) {
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
    public ResponseEntity<String> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        teamService.updateTeam(teamId, team);
        return new ResponseEntity<>("Team updated successfully",HttpStatus.OK);
    }

    @GetMapping("users/{teamId}")
    public ResponseEntity<List<UserDTO>> getTeamUsers(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getUsers().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("posts/{teamId}")
    public ResponseEntity<List<PostDTO>> getTeamPosts(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getPosts().stream().map(post -> modelMapper.map(post, PostDTO.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("events/{teamId}")
    public ResponseEntity<List<EventDTO>> getTeamEvents(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId).getEvents().stream().map(event -> modelMapper.map(event, EventDTO.class)).toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams().stream().map(team -> modelMapper.map(team, TeamDTO.class)).toList(), HttpStatus.OK);
    }
}

package org.pollub.campusmate.team.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.mapper.EventMapper;
import org.pollub.campusmate.post.dto.PostCreationDto;
import org.pollub.campusmate.post.mapper.PostCreationMapper;
import org.pollub.campusmate.team.dto.TeamDto;
import org.pollub.campusmate.team.mapper.TeamMapper;
import org.pollub.campusmate.team.service.TeamService;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final PostCreationMapper postCreationMapper;
    private final EventMapper eventMapper;
    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable long teamId) {
        Team team = teamService.getTeam(teamId);
        TeamDto teamToDisplay = teamMapper.toDto(team);
        return new ResponseEntity<>(teamToDisplay, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody TeamDto teamDTO) {
        Team createdTeam = teamMapper.toEntity(teamDTO);
        teamService.addTeam(createdTeam);
        return new ResponseEntity<>("Team created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{teamId}/addUser/{userId}")
    public ResponseEntity<String> addTeamUser(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.addTeamUser(teamId, userId);
        return new ResponseEntity<>("User added to team successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>("Team deleted successfully", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{teamId}/removeUser/{userId}")
    public ResponseEntity<String> removeTeamUser(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.removeUserFromTeam(teamId, userId);
        return new ResponseEntity<>("User removed from team successfully", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<String> updateTeam(@PathVariable Long teamId, @RequestBody TeamDto teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        teamService.updateTeam(teamId, team);
        return new ResponseEntity<>("Team updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{teamId}/users")
    public ResponseEntity<List<UserDto>> getTeamUsers(@PathVariable Long teamId) {
        List<UserDto> userDtos = teamService.getTeam(teamId).getUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/posts")
    public ResponseEntity<List<PostCreationDto>> getTeamPosts(@PathVariable Long teamId) {
        List<PostCreationDto> postDtos = teamService.getTeam(teamId).getPosts().stream()
                .map(postCreationMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/events")
    public ResponseEntity<List<EventDto>> getTeamEvents(@PathVariable Long teamId) {
        List<EventDto> eventDtos = teamService.getTeam(teamId).getEvents().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
}

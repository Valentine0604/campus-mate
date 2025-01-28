package org.pollub.campusmate.team.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.mapper.EventMapper;
import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.post.mapper.PostMapper;
import org.pollub.campusmate.team.dto.TeamDto;
import org.pollub.campusmate.team.dto.UserIdsRequest;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.team.mapper.TeamMapper;
import org.pollub.campusmate.team.service.TeamService;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.mapper.UserMapper;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final EventMapper eventMapper;
    private final TeamService teamService;
    private final UserService userService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable long teamId) {
        Team team = teamService.getTeam(teamId);
        TeamDto teamToDisplay = teamMapper.toDto(team);
        return new ResponseEntity<>(teamToDisplay, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody TeamDto teamDTO) {
        var createdTeam = teamMapper.toEntity(teamDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            var email = authentication.getName();
            var currentUser = userService.getUserByEmail(email);

            if(currentUser != null) {
                createdTeam.setCreatorId(currentUser.getUserId());
            }

            teamService.addTeam(createdTeam);
            assert currentUser != null;
            teamService.addTeamUser(createdTeam.getTeamId(), currentUser.getUserId());
        }
        else {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Team created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{teamId}/addUsers")
    public ResponseEntity<String> addTeamUser(@PathVariable Long teamId, @RequestBody UserIdsRequest request) {
        Set<User> teamUsers = teamService.getTeam(teamId).getUsers();

        for (Long userId : request.getUserIds()) {
            if(!teamUsers.contains(userService.getUser(userId))) {
                teamService.addTeamUser(teamId, userId);
            } else {
                return new ResponseEntity<>("One of the users has already been added to team", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("User added to team successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>("Team deleted successfully", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{teamId}/removeUser/{userId}")
    public ResponseEntity<String> removeTeamUser(@PathVariable Long teamId, @PathVariable Long userId) {

        if(Objects.equals(userId, teamService.getTeam(teamId).getCreatorId())) {
            return new ResponseEntity<>("Cannot remove creator from team", HttpStatus.BAD_REQUEST);
        }

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
    public ResponseEntity<List<PostDto>> getTeamPosts(@PathVariable Long teamId) {
        List<PostDto> postDtos = teamService.getTeam(teamId).getPosts().stream()
                .map(postMapper::toDto)
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

    @GetMapping("/{creatorId}/teams")
    public ResponseEntity<List<TeamDto>> getTeamsByCreatorId(@PathVariable Long creatorId) {
        List<TeamDto> teams = teamService.getTeamsByCreatorId(creatorId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
}

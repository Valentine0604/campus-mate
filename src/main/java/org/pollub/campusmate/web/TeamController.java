package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Team;
import org.pollub.campusmate.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeam(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody Team team) {
        teamService.addTeam(team);
        return new ResponseEntity<>("Team created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>("Team deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }
}

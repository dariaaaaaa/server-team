package com.projectmanagementsystem.serverteam.api;

import com.projectmanagementsystem.serverteam.repo.model.Member;
import com.projectmanagementsystem.serverteam.repo.model.Role;
import com.projectmanagementsystem.serverteam.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Member>> show(@RequestParam(name="projectId") long projectId) {
        final List<Member> team = teamService.fetchTeam(projectId);
        return ResponseEntity.ok(team);
    }

    @RequestMapping(path="/exist", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exist(@RequestParam(name="userId") long userId, @RequestParam(name="projectId") long projectId) {
        try{
            teamService.ifExistById(userId, projectId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.projectmanagementsystem.serverteam.api.dto.Member member) {
        final long userId = member.getUserId();
        final long projectId = member.getProjectId();
        final Role userRole = member.getUserRole();

        try {
            teamService.create(userId, projectId, userRole);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestParam(name="userId") long userId, @RequestParam(name="projectId") long projectId, @RequestBody Member member) {
        final Role role = member.getUserRole();
        try {
            teamService.update(userId, projectId, role);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name="userId") long userId, @RequestParam(name="projectId") long projectId) {
        teamService.delete(userId, projectId);
        return ResponseEntity.noContent().build();
    }
}

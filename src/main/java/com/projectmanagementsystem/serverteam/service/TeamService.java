package com.projectmanagementsystem.serverteam.service;


import com.projectmanagementsystem.serverteam.repo.TeamRepo;
import com.projectmanagementsystem.serverteam.repo.model.Member;
import com.projectmanagementsystem.serverteam.repo.model.Role;
import com.projectmanagementsystem.serverteam.repo.model.TeamId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class TeamService {
    private final TeamRepo teamRepo;

    private final String userBaseUrl = "http://server-user:8081/users";
    private final String projectBaseUrl = "http://server-project:8080/projects";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Member> fetchTeam(long projectId) {
        return teamRepo.findAllByProjectId(projectId);
    }

    public void ifExistById(long userId, long projectId) throws IllegalArgumentException{
        final TeamId teamId = new TeamId(userId, projectId);
        final Optional<Member> maybeMember = teamRepo.findById(teamId);
        if (maybeMember.isEmpty()) throw new IllegalArgumentException("User not found");
    }

    public void create(long userId, long projectId, Role role){
        final String userUrl = String.format( userBaseUrl + "/exist?id=%d", userId);
        final String projectUrl = String.format( projectBaseUrl + "/exist?id=%d", projectId);
        try {
            final int userResponse = restTemplate.exchange(URI.create(userUrl), HttpMethod.HEAD, null, Void.class ).getStatusCodeValue();
        } catch (HttpClientErrorException e) { throw new IllegalArgumentException("User not found");}

        try {
            final int projectResponse = restTemplate.exchange(URI.create(projectUrl), HttpMethod.HEAD, null, Void.class ).getStatusCodeValue();
        } catch (HttpClientErrorException e) { throw new IllegalArgumentException("Project not found");}

        final TeamId teamId = new TeamId(userId, projectId);
        final Optional<Member> maybeMember = teamRepo.findById(teamId);
        if (maybeMember.isPresent()) throw new IllegalArgumentException("User already member of the project");

        final Member member = new Member(userId, projectId, role);
        teamRepo.save(member);

    }

    public void update(long userId, long projectId, Role role) throws IllegalArgumentException{
        final TeamId teamId = new TeamId(userId, projectId);
        final Optional<Member> maybeMember = teamRepo.findById(teamId);
        if (maybeMember.isEmpty()) throw new IllegalArgumentException("User is not a member of the project");

        final Member member = maybeMember.get();
        if (role != null) member.setUserRole(role);
        teamRepo.save(member);
    }

    public void delete(long userId, long projectId) {
        final TeamId teamId = new TeamId(userId, projectId);
        teamRepo.deleteById(teamId);
    }

}

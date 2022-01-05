package com.projectmanagementsystem.serverteam.service;


import com.projectmanagementsystem.serverteam.repo.TeamRepo;
import com.projectmanagementsystem.serverteam.repo.model.Member;
import com.projectmanagementsystem.serverteam.repo.model.Role;
import com.projectmanagementsystem.serverteam.repo.model.TeamId;
import com.projectmanagementsystem.serverteam.service.requests.ProjectRequests;
import com.projectmanagementsystem.serverteam.service.requests.UserRequests;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class TeamService {
    private final TeamRepo teamRepo;

    public List<Member> fetchTeam(long projectId) {
        return teamRepo.findAllByProjectId(projectId);
    }

    public void ifExistById(long userId, long projectId) throws IllegalArgumentException{
        final TeamId teamId = new TeamId(userId, projectId);
        final Optional<Member> maybeMember = teamRepo.findById(teamId);
        if (maybeMember.isEmpty()) throw new IllegalArgumentException("User not found");
    }

    public void create(long userId, long projectId, Role role){
        UserRequests.checkForExisting(userId);
        ProjectRequests.checkForExisting(projectId);

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

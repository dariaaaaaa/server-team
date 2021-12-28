package com.projectmanagementsystem.serverteam.repo;

import com.projectmanagementsystem.serverteam.repo.model.Member;
import com.projectmanagementsystem.serverteam.repo.model.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Member, TeamId> {
    @Query
    List<Member> findAllByProjectId(long projectId);
}

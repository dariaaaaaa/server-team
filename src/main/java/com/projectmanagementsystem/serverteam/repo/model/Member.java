package com.projectmanagementsystem.serverteam.repo.model;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="teams")
@IdClass(TeamId.class)
public class Member {
    @Id
    @NotNull
    private long userId;
    @Id
    @NotNull
    private long projectId;

    @NotNull
    private Role userRole;

    public Member() {
    }

    public Member(long userId, long projectId, Role userRole) {
        this.userId = userId;
        this.projectId = projectId;
        this.userRole = userRole;
    }

    public long getUserId() {
        return userId;
    }

    public long getProjectId() {
        return projectId;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}

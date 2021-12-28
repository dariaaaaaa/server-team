package com.projectmanagementsystem.serverteam.repo.model;


import java.io.Serializable;
import java.util.Objects;

public final class TeamId implements Serializable {
    private long userId;
    private long projectId;

    public TeamId() {}

    public TeamId(long userId, long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamId teamId = (TeamId) o;
        return userId == teamId.userId && projectId == teamId.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId);
    }
}

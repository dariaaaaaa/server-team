package com.projectmanagementsystem.serverteam.api.dto;

import com.projectmanagementsystem.serverteam.repo.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {
    private long userId;
    private long projectId;
    private Role userRole;
}

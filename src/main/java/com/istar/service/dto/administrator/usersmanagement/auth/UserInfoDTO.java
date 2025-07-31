package com.istar.service.dto.administrator.usersmanagement.auth;

import com.istar.service.dto.administrator.usersmanagement.permission.FeaturePermissionDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO {
    private Long userId;
    private String username;
    private String token;
    private String role;
    private String name;
    private String email;
    private String mobile;
    private String status;
    private String createdTime;
    private String updatedTime;
    private List<FeaturePermissionDTO> permissions;

    // Add constructor
    public UserInfoDTO(Long userId, String username, String token, String role, List<FeaturePermissionDTO> permissions) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.role = role;
        this.permissions = permissions;
    }

    // Default no-args constructor
    public UserInfoDTO() {
    }
}
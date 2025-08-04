package com.istar.service.controller.administrator.usersmanagement.permission;

import com.istar.service.dto.administrator.usersmanagement.permission.UserFeaturePermissionDTO;
import com.istar.service.security.CustomUserDetails;
import com.istar.service.service.administrator.usersmanagement.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/me")
    public List<UserFeaturePermissionDTO> getMyPermissions(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId(); // or cast your UserDetails accordingly
        return permissionService.getUserPermissions(userId);
    }
}

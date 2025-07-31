package com.istar.service.controller.user;

import com.istar.service.entity.permission.Role;
import com.istar.service.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @GetMapping("/{userId}/roles")
    public List<Role> getRolesForUser(@PathVariable Long userId) {
        return userRoleService.getRolesForUser(userId);
    }
}

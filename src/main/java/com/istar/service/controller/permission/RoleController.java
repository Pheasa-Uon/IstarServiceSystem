package com.istar.service.controller.permission;

import com.istar.service.entity.permission.Permission;
import com.istar.service.entity.permission.Role;
import com.istar.service.service.permission.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @GetMapping("/{roleId}/permissions")
    public List<Permission> getPermissions(@PathVariable Long roleId) {
        return roleService.getPermissionsForRole(roleId);
    }
}

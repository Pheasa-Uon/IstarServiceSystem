package com.istar.service.controller.permission;

import com.istar.service.entity.permission.Permission;
import com.istar.service.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    public List<Permission> list() {
        return permissionService.getAllPermissions();
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }
}

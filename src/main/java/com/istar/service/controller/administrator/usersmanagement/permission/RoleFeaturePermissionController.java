package com.istar.service.controller.administrator.usersmanagement.permission;


import com.istar.service.entity.administrator.usersmanagement.permission.RoleFeaturePermission;
import com.istar.service.entity.administrator.usersmanagement.permission.Role;
import com.istar.service.repository.administrator.usersmanagement.permission.RoleRepository;
import com.istar.service.service.administrator.usersmanagement.permission.RoleFeaturePermissionService;
import com.istar.service.dto.administrator.usersmanagement.permission.FeaturePermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
public class RoleFeaturePermissionController {

    @Autowired
    private RoleFeaturePermissionService permissionService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<List<RoleFeaturePermission>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<FeaturePermissionDTO>> getPermissionsByRole(@PathVariable Long roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("Role found: " + role.getName());
        List<FeaturePermissionDTO> permissions = permissionService.getPermissionsByRole(role);
        // Add null check and filtering
        if (permissions != null) {
            permissions = permissions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }
        System.out.println("Permissions found: " + permissions);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    public ResponseEntity<RoleFeaturePermission> createPermission(@RequestBody RoleFeaturePermission permission) {
        return ResponseEntity.ok(permissionService.createPermission(permission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleFeaturePermission> updatePermission(
            @PathVariable Long id,
            @RequestBody RoleFeaturePermission permission) {
        return ResponseEntity.ok(permissionService.updatePermission(id, permission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> savePermissionsBulk(@RequestBody List<FeaturePermissionDTO> dtos) {
        permissionService.savePermissionsBulk(dtos);
        return ResponseEntity.ok().build();
    }
}
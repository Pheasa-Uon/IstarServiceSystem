package com.istar.service.service.permission;

import com.istar.service.entity.permission.Permission;
import com.istar.service.entity.permission.Role;
import com.istar.service.repository.permission.RolePermissionRepository;
import com.istar.service.repository.permission.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Permission> getPermissionsForRole(Long roleId) {
        return rolePermissionRepository.findByRoleId(roleId).stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }
}

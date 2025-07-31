package com.istar.service.service.permission;

import com.istar.service.entity.permission.Permission;
import com.istar.service.repository.permission.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
}

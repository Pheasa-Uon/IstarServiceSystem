package com.istar.service.service.administrator.usersmanagement.permission;


import com.istar.service.dto.administrator.usersmanagement.permission.FeaturePermissionDTO;
import com.istar.service.entity.administrator.usersmanagement.permission.RoleFeaturePermission;
import com.istar.service.repository.administrator.usersmanagement.permission.RoleFeaturePermissionRepository;
import com.istar.service.repository.administrator.usersmanagement.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserRoleRepository userRoleRepository;
    private final RoleFeaturePermissionRepository roleFeaturePermissionRepository;

    public List<FeaturePermissionDTO> getMergedPermissionsByUserId(Long userId) {
        List<Long> roleIds = userRoleRepository.findByUserIdAndBStatusTrue(userId).stream()
                .map(userRole -> userRole.getRole().getId())
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) return Collections.emptyList();

        List<RoleFeaturePermission> permissions = roleFeaturePermissionRepository
                .findByRoleIdInAndBStatusIsTrue(roleIds);

        Map<String, FeaturePermissionDTO> merged = new HashMap<>();

        for (RoleFeaturePermission p : permissions) {
            String code = p.getFeature().getCode();

            FeaturePermissionDTO dto = merged.computeIfAbsent(code, k -> {
                FeaturePermissionDTO f = new FeaturePermissionDTO();
                f.setFeatureCode(code);
                return f;
            });

            dto.setIsAdd(dto.getIsAdd() || Boolean.TRUE.equals(p.getIsAdd()));
            dto.setIsEdit(dto.getIsEdit() || Boolean.TRUE.equals(p.getIsEdit()));
            dto.setIsViewed(dto.getIsViewed() || Boolean.TRUE.equals(p.getIsViewed()));
            dto.setIsDeleted(dto.getIsDeleted() || Boolean.TRUE.equals(p.getIsDeleted()));
            dto.setIsSave(dto.getIsSave() || Boolean.TRUE.equals(p.getIsSave()));
            dto.setIsCancel(dto.getIsCancel() || Boolean.TRUE.equals(p.getIsCancel()));
        }

        return new ArrayList<>(merged.values());
    }
}

package com.istar.service.service.administrator.usersmanagement.permission;

import com.istar.service.dto.administrator.usersmanagement.permission.UserFeaturePermissionDTO;
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

    public List<UserFeaturePermissionDTO> getUserPermissions(Long userId) {
        List<Long> roleIds = userRoleRepository.findByUserIdAndBStatusTrue(userId)
                .stream().map(ur -> ur.getRole().getId()).collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<RoleFeaturePermission> permissions = roleFeaturePermissionRepository
                .findByRoleIdInAndBStatusIsTrue(roleIds);

        Map<String, UserFeaturePermissionDTO> merged = new HashMap<>();

        for (RoleFeaturePermission p : permissions) {
            String code = p.getFeature().getCode();

            UserFeaturePermissionDTO dto = merged.computeIfAbsent(code, k -> new UserFeaturePermissionDTO(
                    code, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
            ));

            dto.setIsSearch(dto.getIsSearch() || Boolean.TRUE.equals(p.getIsSearch()));
            dto.setIsAdd(dto.getIsAdd() || Boolean.TRUE.equals(p.getIsAdd()));
            dto.setIsViewed(dto.getIsViewed() || Boolean.TRUE.equals(p.getIsViewed()));
            dto.setIsEdit(dto.getIsEdit() || Boolean.TRUE.equals(p.getIsEdit()));
            dto.setIsApprove(dto.getIsApprove() || Boolean.TRUE.equals(p.getIsApprove()));
            dto.setIsReject(dto.getIsReject() || Boolean.TRUE.equals(p.getIsReject()));
            dto.setIsDeleted(dto.getIsDeleted() || Boolean.TRUE.equals(p.getIsDeleted()));
            dto.setIsSave(dto.getIsSave() || Boolean.TRUE.equals(p.getIsSave()));
            dto.setIsClear(dto.getIsClear() || Boolean.TRUE.equals(p.getIsClear()));
            dto.setIsCancel(dto.getIsCancel() || Boolean.TRUE.equals(p.getIsCancel()));
            dto.setIsProcess(dto.getIsProcess() || Boolean.TRUE.equals(p.getIsProcess()));
            dto.setIsImport(dto.getIsImport() || Boolean.TRUE.equals(p.getIsImport()));
            dto.setIsExport(dto.getIsExport() || Boolean.TRUE.equals(p.getIsExport()));
        }

        return new ArrayList<>(merged.values());
    }
}

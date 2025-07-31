package com.istar.service.repository.permission;

import com.istar.service.entity.permission.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findByRoleId(Long roleId);

//    @Query("SELECT rp FROM RolePermission rp JOIN FETCH rp.permission WHERE rp.role.id = :roleId")
//    List<RolePermission> findByRoleIdWithPermissions(@Param("roleId") Long roleId);

}

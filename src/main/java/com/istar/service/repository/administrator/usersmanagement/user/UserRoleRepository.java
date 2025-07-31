package com.istar.service.repository.administrator.usersmanagement.user;

import com.istar.service.entity.administrator.usersmanagement.permission.Role;
import com.istar.service.entity.administrator.usersmanagement.user.User;
import com.istar.service.entity.administrator.usersmanagement.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(Long userId);
    //Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.id = :roleId")
    Optional<UserRole> findByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    Optional<UserRole> findByUserAndRole(User user, Role role);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId AND ur.bStatus = true")
    List<UserRole> findByUserIdAndBStatusTrue(@Param("userId") Long userId);
}
package com.istar.service.controller.administrator.usersmanagement.user;


import com.istar.service.entity.administrator.usersmanagement.permission.Role;
import com.istar.service.entity.administrator.usersmanagement.user.UserRole;
import com.istar.service.service.administrator.usersmanagement.user.UserRoleService;
import com.istar.service.dto.administrator.usersmanagement.user.RoleAssignmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userroles")
@PreAuthorize("hasRole('ADMIN')")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/assign")
    public ResponseEntity<UserRole> assignRole(@RequestBody RoleAssignmentRequest request) {
        UserRole userRole = userRoleService.assignRoleToUser(request.userId, request.roleId);
        return ResponseEntity.ok(userRole);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeRole(@RequestBody RoleAssignmentRequest request) {
        userRoleService.removeRoleFromUser(request.userId, request.roleId);
        return ResponseEntity.ok("Role removed from user");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable Long userId) {
        List<Role> roles = userRoleService.getUserRoles(userId);
        return ResponseEntity.ok(roles);
    }
}

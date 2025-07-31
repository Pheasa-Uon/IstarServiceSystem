package com.istar.service.entity.permission;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "sys_role_permissions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id", "permission_id"})
})
@Data
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

}


package com.istar.service.entity.permission;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "sys_permissions")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;  // e.g., isAdd, isEdit
    private String description;
}

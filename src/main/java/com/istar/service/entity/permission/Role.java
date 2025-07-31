package com.istar.service.entity.permission;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "sys_roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role_name;
}

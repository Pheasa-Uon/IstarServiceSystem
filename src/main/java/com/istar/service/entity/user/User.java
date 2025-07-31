package com.istar.service.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sys_users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    private boolean enabled = true;
}

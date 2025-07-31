package com.istar.service.service.user;

import com.istar.service.entity.permission.Role;
import com.istar.service.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public List<Role> getRolesForUser(Long userId) {
        return userRoleRepository.findByUserId(userId).stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
}

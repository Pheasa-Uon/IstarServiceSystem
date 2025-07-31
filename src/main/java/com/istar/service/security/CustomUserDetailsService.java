package com.istar.service.security;

import com.istar.service.entity.administrator.usersmanagement.permission.Role;
import com.istar.service.entity.administrator.usersmanagement.permission.RoleFeaturePermission;
import com.istar.service.entity.administrator.usersmanagement.user.User;
import com.istar.service.repository.administrator.usersmanagement.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())  // already BCrypt encoded
//                .authorities("USER")
//                .build();
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isBStatus()) {
            throw new UsernameNotFoundException("User is inactive");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isAdmin()) {
            // Full access roles for admin
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ALL_ACCESS"));
        } else {
            // Assign permissions based on role feature permissions
            Role role = user.getRole();
            if (role != null && role.getRoleFeaturePermissions() != null) {
                for (RoleFeaturePermission perm : role.getRoleFeaturePermissions()) {
                    String featureKey = perm.getFeature().getCode();

                    if (perm.getIsSearch() != null && perm.getIsSearch()) authorities.add(new SimpleGrantedAuthority(featureKey + "_SEARCH"));
                    if (perm.getIsViewed() != null && perm.getIsViewed()) authorities.add(new SimpleGrantedAuthority(featureKey + "_VIEWED"));
                    if (perm.getIsAdd() != null && perm.getIsAdd()) authorities.add(new SimpleGrantedAuthority(featureKey + "_ADD"));
                    if (perm.getIsEdit() != null && perm.getIsEdit()) authorities.add(new SimpleGrantedAuthority(featureKey + "_EDIT"));
                    if (perm.getIsApprove() != null && perm.getIsApprove()) authorities.add(new SimpleGrantedAuthority(featureKey + "_APPROVE"));
                    if (perm.getIsReject() != null && perm.getIsReject()) authorities.add(new SimpleGrantedAuthority(featureKey + "_REJECT"));
                    if (perm.getIsDeleted() != null && perm.getIsDeleted()) authorities.add(new SimpleGrantedAuthority(featureKey + "_DELETE"));
                    if (perm.getIsSave() != null && perm.getIsSave()) authorities.add(new SimpleGrantedAuthority(featureKey + "_SAVE"));
                    if (perm.getIsClear() != null && perm.getIsClear()) authorities.add(new SimpleGrantedAuthority(featureKey + "_CLEAR"));
                    if (perm.getIsCancel() != null && perm.getIsCancel()) authorities.add(new SimpleGrantedAuthority(featureKey + "_CANCEL"));
                    if (perm.getIsProcess() != null && perm.getIsProcess()) authorities.add(new SimpleGrantedAuthority(featureKey + "_PROCESS"));
                    if (perm.getIsImport() != null && perm.getIsImport()) authorities.add(new SimpleGrantedAuthority(featureKey + "_IMPORT"));
                    if (perm.getIsExport() != null && perm.getIsExport()) authorities.add(new SimpleGrantedAuthority(featureKey + "_EXPORT"));
                }
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
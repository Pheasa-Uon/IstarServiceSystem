package com.istar.service.security;

import com.istar.service.entity.administrator.usersmanagement.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    public String getUserCode() {
        return user.getUserCode();
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Customize if needed. You can map role/permissions here
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();  // or email if that's your login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust if you track this in your user table
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Or return user.getEnabled() if you track that
    }
}

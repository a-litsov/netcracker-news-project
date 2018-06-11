package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

// used by spring security as representation of user
public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(user.getRole());
        authorities.addAll(user.getRole().getOperations());

        return authorities;
    }

    public Integer getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !"ROLE_BANNED".equals(this.user.getRole().getAuthority());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasAuthority(String authority) {
        return getAuthorities().stream()
                               .anyMatch(a -> Objects.equals(authority, a.getAuthority()));
    }

    public boolean hasAnyAuthority(String... authorities) {
        for (String authority : authorities) {
            for (GrantedAuthority ourAuthority : getAuthorities()) {
                if (Objects.equals(ourAuthority.getAuthority(), authority)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasRole(String role) {
        return Objects.equals("ROLE_" + role, user.getRole().getAuthority());
    }

    public boolean hasAnyRole(String... roles) {
        return Arrays.stream(roles)
                     .anyMatch(r -> Objects.equals("ROLE_" + r, user.getRole().getAuthority()));

    }
}
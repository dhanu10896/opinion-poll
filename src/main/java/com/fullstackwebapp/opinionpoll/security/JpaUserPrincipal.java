package com.fullstackwebapp.opinionpoll.security;

import com.fullstackwebapp.opinionpoll.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class JpaUserPrincipal implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public JpaUserPrincipal(User user) {
        this.user = user;
        this.authorities = new ArrayList<>();
        if (user.getRoles()!= null){
            this.authorities = user.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getName().name())
            ).collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }

    public Long getId() {
        return user.getId();
    }
}

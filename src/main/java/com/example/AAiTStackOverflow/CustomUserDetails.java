package com.example.AAiTStackOverflow;

import java.util.*;
import java.util.stream.Collectors;

import com.example.AAiTStackOverflow.Domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Relation;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Account user;

    public CustomUserDetails(Account user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public static enum Role{
        ADMIN,USER,;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return authorities;
    }

    @Override
    public String getPassword () {
        return user.getPassword();
    }

    @Override
    public String getUsername () {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }

    public String getFullName () {
        return user.getFirstName() + " " + user.getLastName();
    }

}

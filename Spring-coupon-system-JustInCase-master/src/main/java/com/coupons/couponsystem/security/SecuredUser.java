package com.coupons.couponsystem.security;

import com.coupons.couponsystem.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class SecuredUser implements UserDetails {
    private User user;

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassWord();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SecurityAuthority> authoreties = new LinkedList<>();
        authoreties.add(new SecurityAuthority(user));
        return authoreties;
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
}

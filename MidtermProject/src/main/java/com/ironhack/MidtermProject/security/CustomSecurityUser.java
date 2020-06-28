package com.ironhack.MidtermProject.security;

import com.ironhack.MidtermProject.model.security.Role;
import com.ironhack.MidtermProject.model.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomSecurityUser extends User implements UserDetails {
    private static final long serialVersionUID = -4381938875186527688L;

    /**
     * CustomerSecurity Constructor
     * @param user a User element
     */
    public CustomSecurityUser(User user) {
        System.out.println(user);
        this.setRoles(user.getRoles());
        this.setId(user.getId());
        this.setPassword(user.getPassword());
        this.setUsername(user.getUsername());
    }

    /**
     * This method gets a collection whose elements are roles which have authority to do actions in system.
     * @return A collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>(); //Authority son los roles que tendrá nuestro user
        Set<Role> roles = this.getRoles();//cada user tiene un set que roles es eso que estamos definindo
        System.out.println(this);
        for (Role role : roles) {
            System.out.println(role.getRole());
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    /**
     * This method checks  if a Account isn't expired.
     * @return a boolean value
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This method checks if a Account isn't locked.
     * @return a boolean value
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This method check if  a Credential isn't expired.
     * @return a boolean value
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * This method check if a user is enable.
     * @return a boolean value
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
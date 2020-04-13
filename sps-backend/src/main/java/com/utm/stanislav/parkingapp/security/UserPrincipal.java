package com.utm.stanislav.parkingapp.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Builder
@ToString
@AllArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    
    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = generateAuthoritiesForUser(user);
        return new UserPrincipalBuilder()
                       .id(user.getId())
                       .email(user.getEmail())
                       .firstName(user.getFirstName())
                       .lastName(user.getLastName())
                       .username(user.getUsername())
                       .email(user.getPhoneNumber())
                       .password(user.getPassword())
                       .authorities(authorities)
                       .build();
    }
    
    private static List<GrantedAuthority> generateAuthoritiesForUser(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()));
        }
        return grantedAuthorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (!(obj instanceof UserPrincipal)) return false;
        
        UserPrincipal userPrincipal = (UserPrincipal) obj;
        
        return Objects.equals(id, userPrincipal.id);
    }
}

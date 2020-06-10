package com.utm.stanislav.parkingapp.web.controller.booking;

import com.utm.stanislav.parkingapp.fixture.RoleFixture;
import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.List;

public class WithMockCustomUserPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUserPrincipal> {
    
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUserPrincipal withMockCustomUserPrincipal) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
    
        Role role = RoleFixture.withName("USER").get();
        List<Role> roles = Collections.singletonList(role);
        User user = UserFixture.usernameOnly("user").get();
        user.setRoles(roles);
        UserPrincipal userPrincipal = UserPrincipal.create(user);
    
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "password", userPrincipal.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }
}

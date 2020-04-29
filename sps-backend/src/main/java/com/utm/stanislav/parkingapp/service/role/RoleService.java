package com.utm.stanislav.parkingapp.service.role;

import com.utm.stanislav.parkingapp.model.Role;

import java.util.Optional;

public interface RoleService {
    
    Optional<Role> getRoleByName(String name);
    Role getBaseRole();
}

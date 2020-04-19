package com.utm.stanislav.parkingapp.service.role;

import com.utm.stanislav.parkingapp.exceptions.RoleNotFoundException;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private static final String BASE_ROLE = "USER";
    
    private RoleRepository roleRepository;
    
    @Override
    @Transactional
    public Optional<Role> getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
    
    @Override
    @Transactional
    public Role getBaseRole() {
        return roleRepository.findByName(BASE_ROLE).get();
    }
}

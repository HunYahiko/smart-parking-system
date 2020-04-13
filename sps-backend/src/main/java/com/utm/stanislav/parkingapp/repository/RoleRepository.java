package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}

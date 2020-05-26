package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}

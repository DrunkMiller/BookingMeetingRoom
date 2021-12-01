package com.booking.repositories.postgres;

import com.booking.models.postgres.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
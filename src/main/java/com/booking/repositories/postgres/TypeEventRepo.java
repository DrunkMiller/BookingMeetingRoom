package com.booking.repositories.postgres;

import com.booking.models.postgres.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeEventRepo extends JpaRepository<TypeEvent, Long> {
    TypeEvent findByType(String type);

}

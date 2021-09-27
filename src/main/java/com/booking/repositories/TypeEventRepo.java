package com.booking.repositories;

import com.booking.models.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeEventRepo extends JpaRepository<TypeEvent, Long> {
    TypeEvent findByType(String type);

}

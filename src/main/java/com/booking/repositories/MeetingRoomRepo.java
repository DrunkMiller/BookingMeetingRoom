package com.booking.repositories;

import com.booking.models.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepo extends JpaRepository<MeetingRoom, Long> {
}

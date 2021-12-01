package com.booking.repositories.postgres;

import com.booking.models.postgres.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepo extends JpaRepository<MeetingRoom, Long> {
}

package com.booking.repositories.mongodb;

import com.booking.models.mongodb.MeetingRoomReservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomReservationRepo extends MongoRepository<MeetingRoomReservation, String> {
}

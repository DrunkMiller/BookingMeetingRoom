package com.booking.models.mongodb;

import com.booking.dto.MeetingRoomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingRoomReservation {
    @Id
    private String id;
    private LocalDateTime dateCreated;
    private MeetingRoomDto meetingRoomDto;
    private boolean successfulReserve;

}

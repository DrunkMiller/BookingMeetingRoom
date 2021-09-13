package com.booking.models;

import lombok.Data;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Set;

@Entity
@Data
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Integer numberOfSeats;
    private boolean hasInteractiveBoard;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar workTimeWith;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar workTimeBy;
    private boolean isWorking;

    @ElementCollection(targetClass = TypeEvent.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "type_events", joinColumns = @JoinColumn(name = "meeting_room_id"))
    @Enumerated(EnumType.STRING)
    private Set<TypeEvent> typeEventSet;
}

package com.booking.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Integer numberOfSeats;
    private boolean hasInteractiveBoard;
    private LocalDateTime workTimeWith;
    private LocalDateTime workTimeBy;
    private boolean isWorking;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meeting_type", joinColumns = @JoinColumn(name = "meeting_room_id"),
            inverseJoinColumns = @JoinColumn(name = "type_event_id"))
    private Set<TypeEvent> typeEventSet;
}

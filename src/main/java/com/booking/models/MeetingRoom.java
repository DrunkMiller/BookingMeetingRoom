package com.booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Integer numberOfSeats;
    private boolean interactiveBoard;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(columnDefinition = "TIME")
    private LocalTime workTimeWith;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(columnDefinition = "TIME")
    private LocalTime workTimeBy;
    private boolean working = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meeting_type", joinColumns = @JoinColumn(name = "meeting_room_id"),
            inverseJoinColumns = @JoinColumn(name = "type_event_id"))
    private Set<TypeEvent> typeEventSet;


}

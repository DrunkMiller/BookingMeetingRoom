package com.booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
//    @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime workTimeWith;
//    @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime workTimeBy;
    private boolean working;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meeting_type", joinColumns = @JoinColumn(name = "meeting_room_id"),
            inverseJoinColumns = @JoinColumn(name = "type_event_id"))
    private Set<TypeEvent> typeEventSet;


    public MeetingRoom(String title, String description, Integer numberOfSeats, boolean interactiveBoard, LocalDateTime workTimeWith, LocalDateTime workTimeBy, boolean working, Set<TypeEvent> typeEventSet) {
        this.title = title;
        this.description = description;
        this.numberOfSeats = numberOfSeats;
        this.interactiveBoard = interactiveBoard;
        this.workTimeWith = workTimeWith;
        this.workTimeBy = workTimeBy;
        this.working = working;
        this.typeEventSet = typeEventSet;
    }
}

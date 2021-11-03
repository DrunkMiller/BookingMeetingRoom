package com.booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE booking SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime finishTime;
    @JsonIgnore
    private LocalDateTime bookingTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_event_id")
    private TypeEvent typeEvent;

    private boolean deleted = Boolean.FALSE;

    @PrePersist
    public void onCreate() {
        this.setBookingTime(LocalDateTime.now());
    }


}

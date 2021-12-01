package com.booking.models.postgres;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE booking SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(value = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(value = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime finishTime;
    @JsonIgnore
    private LocalDateTime bookingTime;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(hidden = true)
    private User employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_event_id")
    private TypeEvent typeEvent;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private boolean deleted = Boolean.FALSE;

    @PrePersist
    public void onCreate() {
        this.setBookingTime(LocalDateTime.now());
    }


}

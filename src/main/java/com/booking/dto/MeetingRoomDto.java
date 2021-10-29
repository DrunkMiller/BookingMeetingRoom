package com.booking.dto;

import com.booking.models.TypeEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class MeetingRoomDto {
    private String title;
    private String description;
    private Integer numberOfSeats;
    private boolean interactiveBoard;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime workTimeWith;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime workTimeBy;
    private boolean working = true;
    private Set<TypeEventDto> typeEventSet;
}

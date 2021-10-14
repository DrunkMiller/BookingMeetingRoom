package com.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class TypeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToMany(mappedBy = "typeEventSet")
    @JsonIgnore
    private Set<MeetingRoom> meetingRoomSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeEvent typeEvent = (TypeEvent) o;
        return Objects.equals(id, typeEvent.id) && Objects.equals(type, typeEvent.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, meetingRoomSet);
    }
}

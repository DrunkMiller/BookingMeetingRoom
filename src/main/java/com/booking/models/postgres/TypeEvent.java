package com.booking.models.postgres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class TypeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(value = "Type event",required = true)
    private String type;
    @JsonIgnore
    @ManyToMany(mappedBy = "typeEventSet")
    @ApiModelProperty(hidden = true)
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

    @Override
    public String toString() {
        return "TypeEvent{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", meetingRoomSet=" + meetingRoomSet +
                '}';
    }
}

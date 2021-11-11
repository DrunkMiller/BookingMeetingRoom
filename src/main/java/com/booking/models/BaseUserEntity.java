package com.booking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created")
    private LocalDate created;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "updated")
    private LocalDate updated;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @PrePersist
    public void onCreate() {
        this.setCreated(LocalDate.now());
        this.setStatus(Status.ACTIVE);
        this.setUpdated(LocalDate.now());
    }

    @PreUpdate
    public void onUpdate() {
        this.setUpdated(LocalDate.now());
    }


}


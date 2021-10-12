package com.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseUserEntity {

    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    private String firstName;
    @NotNull(message = "should not be null")
    @NotEmpty(message = "should not be empty")
    private String lastName;
    private String username;
    @Size(min = 3, message = "Too short password!")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}

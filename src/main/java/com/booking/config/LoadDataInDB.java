package com.booking.config;

import com.booking.models.postgres.*;
import com.booking.repositories.postgres.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class LoadDataInDB implements CommandLineRunner {

    private final DataSource dataSource;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final TypeEventRepo typeEventRepo;
    private final MeetingRoomRepo meetingRoomRepo;
    private final BookingRepo bookingRepo;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    public LoadDataInDB(DataSource dataSource, RoleRepo roleRepo, UserRepo userRepo, TypeEventRepo typeEventRepo, MeetingRoomRepo meetingRoomRepo, BookingRepo bookingRepo) {
        this.dataSource = dataSource;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.typeEventRepo = typeEventRepo;
        this.meetingRoomRepo = meetingRoomRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        int countUser = JdbcTestUtils.countRowsInTable(new JdbcTemplate(dataSource), "users");
        if (countUser == 0 && ddlAuto.equals("create")) {
            System.out.println("1");
            Role roleAdmin = new Role("ROLE_ADMIN", null);
            roleRepo.saveAll(Arrays.asList(roleAdmin, new Role("ROLE_USER", null),
                    new Role("ROLE_LECTOR", null)));
            User user = new User("Ivan", "Ivanov", "ivan",
                    "$2a$12$/PFCP5A1A0CVx6HLnwSGd..tR701Elbencu5hBQcjDK1lPraGoLfC", new ArrayList<>(Collections.singletonList(roleAdmin)));
            userRepo.save(user);
            TypeEvent typeEventMeet = new TypeEvent(null, "Meeting", new HashSet<>());
            TypeEvent typeEventPres = new TypeEvent(null, "Presentation", new HashSet<>());
            typeEventRepo.saveAll(Arrays.asList(typeEventMeet, typeEventPres));
            MeetingRoom meetingRoom1 = new MeetingRoom(null, "Tokio", "For meeting and presentation",
                    3, true, LocalTime.of(10, 0), LocalTime.of(19, 0), true,
                    new HashSet<>(Arrays.asList(typeEventMeet, typeEventPres)));
            MeetingRoom meetingRoom2 = new MeetingRoom(null, "Paris", "For presentation",
                    9, true, LocalTime.of(8, 0), LocalTime.of(17, 0), true,
                    new HashSet<>(Collections.singletonList(typeEventPres)));
            meetingRoomRepo.saveAll(Arrays.asList(meetingRoom1, meetingRoom2));
            Booking booking = new Booking(null, "meet", LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now(), user, meetingRoom1, typeEventMeet, false);
            bookingRepo.save(booking);
        }
    }
}

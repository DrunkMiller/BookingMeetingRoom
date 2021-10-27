INSERT INTO ROLES(ID,

                  CREATED,
                  STATUS,
                  UPDATED,
                  NAME)
VALUES (1, '25.10.2021', 'ACTIVE', '25.10.2021', 'ROLE_ADMIN');


INSERT INTO ROLES(ID,

                  CREATED,
                  STATUS,
                  UPDATED,
                  NAME)
VALUES (2, '25.10.2021', 'ACTIVE', '25.10.2021', 'ROLE_USER');


INSERT INTO ROLES(ID,

                  CREATED,
                  STATUS,
                  UPDATED,
                  NAME)
VALUES (3, '25.10.2021', 'ACTIVE', '25.10.2021', 'ROLE_LECTOR');


INSERT INTO USERS(ID,

                  CREATED,
                  STATUS,
                  UPDATED,
                  FIRST_NAME,
                  LAST_NAME,
                  PASSWORD,
                  USERNAME)
VALUES (1, '25.10.2021', 'ACTIVE', '25.10.2021', 'Ivan', 'Ivanov', '$2a$10$5p/Z0erMLKTAaSDOM0.MMu1Whitj3ZoWsjc.sfMgpyU/h9jPG4MIq', 'ivan'),
       (2, '25.10.2021', 'ACTIVE', '25.10.2021', 'Petr', 'Petrov', '$2a$10$5p/Z0erMLKTAaSDOM0.MMu1Whitj3ZoWsjc.sfMgpyU/h9jPG4MIq', 'petr'),
       (3, '25.10.2021', 'ACTIVE', '25.10.2021', 'Nikola', 'Sidorov', '$2a$10$5p/Z0erMLKTAaSDOM0.MMu1Whitj3ZoWsjc.sfMgpyU/h9jPG4MIq', 'nikola'),
       (4, '25.10.2021', 'ACTIVE', '25.10.2021', 'Nikita', 'Nikanorov', '$2a$10$5p/Z0erMLKTAaSDOM0.MMu1Whitj3ZoWsjc.sfMgpyU/h9jPG4MIq', 'nikita'); --password 123

INSERT INTO USER_ROLE(USER_ID,

                      ROLE_ID)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 2),
       (4, 3);


INSERT INTO TYPE_EVENT(ID,

                       TYPE)
VALUES (1, 'Meeting'),
       (2, 'Presentation'),
       (3, 'Watching video');


INSERT INTO MEETING_ROOM(ID,

                         DESCRIPTION,
                         INTERACTIVE_BOARD,
                         NUMBER_OF_SEATS,
                         TITLE,
                         WORK_TIME_BY,
                         WORK_TIME_WITH,
                         WORKING)
VALUES (1, 'Big meeting room', TRUE, 8, 'Tokio', '21:00','09:00', TRUE),
       (2, 'Small meeting room', TRUE, 5, 'Praga', '19:00', '10:00', TRUE),
       (3, 'Relaxed meeting room', TRUE, 4, 'Paris', '23:00','09:00', TRUE);


INSERT INTO MEETING_TYPE(MEETING_ROOM_ID,

                         TYPE_EVENT_ID)
VALUES (1, 1),
       (1,2),
       (2, 2),
       (3,3);


INSERT INTO BOOKING(ID,
                    FINISH_TIME,
                    START_TIME,
                    TITLE,
                    USER_ID,
                    MEETING_ROOM_ID,
                    TYPE_EVENT_ID)
VALUES (1, '2021-10-27T13:00:00', '2021-10-27T11:00:00','la-la-la', 3, 1, 1),
       (2, '2021-10-27T17:00:00', '2021-10-27T13:00:00','la-la-la', 4, 2, 2)
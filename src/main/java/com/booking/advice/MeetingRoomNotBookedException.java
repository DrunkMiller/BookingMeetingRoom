package com.booking.advice;

public class MeetingRoomNotBookedException extends RuntimeException{
    public MeetingRoomNotBookedException(String message) {
        super(message);
    }
}

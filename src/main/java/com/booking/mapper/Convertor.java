package com.booking.mapper;


import com.booking.dto.BookingDto;
import com.booking.dto.MeetingRoomDto;
import com.booking.dto.TypeEventDto;
import com.booking.dto.UserDto;
import com.booking.models.Booking;
import com.booking.models.MeetingRoom;
import com.booking.models.TypeEvent;
import com.booking.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class Convertor {

    private final ModelMapper modelMapper;

    public Convertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(TypeEvent.class, TypeEventDto.class);
        this.modelMapper.createTypeMap(MeetingRoom.class, MeetingRoomDto.class);
        this.modelMapper.createTypeMap(Booking.class, BookingDto.class);
        this.modelMapper.createTypeMap(User.class, UserDto.class);
    }

    public <T, Y> Y convertToDto(T entity, Class<Y> entityDto) {
        return modelMapper.map(entity, entityDto);
    }

}

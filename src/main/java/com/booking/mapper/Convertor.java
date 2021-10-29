package com.booking.mapper;


import com.booking.dto.BookingDto;
import com.booking.dto.MeetingRoomDto;
import com.booking.dto.TypeEventDto;
import com.booking.models.Booking;
import com.booking.models.MeetingRoom;
import com.booking.models.TypeEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Convertor {

    private final ModelMapper modelMapper;

    public Convertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(TypeEvent.class, TypeEventDto.class);
        this.modelMapper.createTypeMap(MeetingRoom.class, MeetingRoomDto.class);
        this.modelMapper.createTypeMap(Booking.class, BookingDto.class);
    }

    public <T, Y> Y convertToDto(T entity, Class<Y> entityDto) {
        return modelMapper.map(entity, entityDto);
    }

}

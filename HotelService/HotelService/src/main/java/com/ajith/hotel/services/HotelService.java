package com.ajith.hotel.services;

import com.ajith.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {
    public Hotel createHotel(Hotel hotel);

    public List<Hotel> getAll();

    public Hotel getHotel(String id);


}

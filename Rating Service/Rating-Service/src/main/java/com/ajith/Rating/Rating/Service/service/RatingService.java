package com.ajith.Rating.Rating.Service.service;

import com.ajith.Rating.Rating.Service.entities.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //get all rating based on UserId

    List<Rating> getRatingByUserId(String userId);

    //get all rating based on HotelId
    List<Rating> getRatingByHotelId(String hotelId);

}

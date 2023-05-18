package com.ajith.Rating.Rating.Service.controllers;

import com.ajith.Rating.Rating.Service.entities.Rating;
import com.ajith.Rating.Rating.Service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    //Todo: Create Rating
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));

    }

    //Get all ratings

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings()
    {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    //Get all ratings by userId
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable(name = "userId") String userId)
    {
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    //Get all ratings by HotelId


    @GetMapping(value = "/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable(name ="hotelId" ) String hotelId)
    {
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }




}

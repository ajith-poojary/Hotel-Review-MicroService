package com.ajith.userService.external.services;

import com.ajith.userService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
//   public Rating createRating(Map<String,Object> values);
// userdefined data typ illa andre, nan hatra userdefined dt ide, i,e rating

    public Rating createRating(Rating values);

    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable String ratingId,Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    public void delete(@PathVariable(name = "ratingId") String ratingId);
}

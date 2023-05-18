package com.ajith.userService;

import com.ajith.userService.entities.Rating;
import com.ajith.userService.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {
	@Autowired
	RatingService ratingService;

	@Test
	void contextLoads() {
	}
	@Test
	void createRating()
	{
		Rating rating=Rating.builder().rating(10).userId("").hotelId("").feedback("feign client").build();
		Rating rating1 = ratingService.createRating(rating);
		System.out.println("new rating created");


	}


}

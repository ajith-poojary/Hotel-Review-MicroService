package com.ajith.userService.service;

import com.ajith.userService.entities.Hotel;
import com.ajith.userService.entities.Rating;
import com.ajith.userService.entities.User;
import com.ajith.userService.exception.ResourceNotFoundException;
import com.ajith.userService.external.services.HotelService;
import com.ajith.userService.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserSevice {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Override
    public User saveUser(User user) {
        String uid = UUID.randomUUID().toString();
        user.setUserId(uid);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            getUser(user.getUserId());
        }

        return allUsers;

    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with the given id is not found on server" + userId));


        /**
         * Fetch the rating from rating service
         * http://localhost:8083/ratings/users/1ec622ca-edbd-4fd2-8b7a-4913d69272d6
         * to call we the end point we have many options, now we are making use of rest template
         * to use rest template, you need to wire it, to auto wire, we need to make sure, a bean is created
         * you can create the bean main class[which is also act as a configuration file] ,
         * or you can configure it in separate configuration file
         */
//        String url="http://localhost:8083/ratings/users/1ec622ca-edbd-4fd2-8b7a-4913d69272d6";
        String url = "http://RATING-SERVICE/ratings/users/" + user.getUserId();

        Rating[] forObject = restTemplate.getForObject(url, Rating[].class);
        List<Rating> ratings = Arrays.stream(forObject).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //1.api call to hotel service

            // http://localhost:8082/hotels/112
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //2.set the hotel to rating

            rating.setHotel(hotel);

            //3.return the rating

            return rating;

        }).collect(Collectors.toList());
        user.setRatings(ratingList);


//        logger.info("rating{}",ratingsOfUser);


        return user;

    }

    @Override
    public User deleteUser(String userId) {
        User retrivedUser = getUser(userId);
        userRepository.delete(retrivedUser);
        return retrivedUser;
    }

    @Override
    public User updateUser(User user) {
        User retrievedUser = getUser(user.getUserId());

        retrievedUser.setName(user.getName());
        retrievedUser.setEmail(user.getEmail());
        retrievedUser.setAbout(user.getAbout());

        userRepository.save(retrievedUser);


        return retrievedUser;
    }
}

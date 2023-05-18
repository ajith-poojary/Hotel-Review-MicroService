package com.ajith.userService.controlers;

import com.ajith.userService.entities.User;
import com.ajith.userService.service.UserServiceImpl;
import com.ajith.userService.service.UserSevice;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserSevice userSevice;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User user1 = userSevice.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);

    }
    int count=1;

    @GetMapping(value = "/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingUser(@PathVariable(name = "userId") String userId)
    {
        System.out.println("retry count-->"+count++);
        User user = userSevice.getUser(userId);
        return ResponseEntity.ok(user);

    }

    //Todo: creating fallback method for circuit breaker, return typ same irbeku

    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex)
    {
        logger.info("Fallback method is called,{}",ex.getMessage());
        logger.info(" {}",ex.getStackTrace());
        System.out.println(ex);
      User user=  User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about(ex.getMessage())
                .userId("1234")
                .build();

        return new ResponseEntity(user,HttpStatus.OK);


    }



    @GetMapping

    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> allUsers = userSevice.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody User user)
//    {
//        User user1 = userSevice.getUser(user.getUserId());
//
//        user1.setName(user.getUserId());
//        user1.setEmail(user.getEmail());
//        user1.setAbout(user.getAbout());
//
//        userSevice.updateUser(user1);
//        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
//
//
//    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable(name = "userId")  String userId)
    {
        User user = userSevice.deleteUser(userId);

        return new ResponseEntity(user,HttpStatus.OK);

    }

}

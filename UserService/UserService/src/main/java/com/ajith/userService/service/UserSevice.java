package com.ajith.userService.service;

import com.ajith.userService.entities.User;

import java.util.List;

public interface UserSevice {

    //Create

    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get user based on Id

    User getUser(String userId);

    //delete user based on Id
    User deleteUser(String userId);

    //update user

    User updateUser(User user);


}

package com.mohammedmaroof.onlinefoodtracker.Controllers;

import com.mohammedmaroof.onlinefoodtracker.entity.Order;
import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    //view all the user list
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userServices.getAll(), HttpStatus.OK);
    }

    //get user details
    @GetMapping("/get-user-details")
    public ResponseEntity<User> getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userServices.findByUserName(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //update by username
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userServices.findByUserName(userName);
        if(oldUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = oldUser;
        user.setUserName(newUser.getUserName() != null && !newUser.getUserName().isEmpty() ?
                newUser.getUserName() : user.getUserName());
        user.setPassword(newUser.getPassword() != null && !newUser.getPassword().isEmpty() ?
                newUser.getPassword(): user.getPassword());
        userServices.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Delete the user
    @DeleteMapping("/delete-user")
    public ResponseEntity<Order> deleteOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userServices.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



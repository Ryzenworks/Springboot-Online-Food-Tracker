package com.mohammedmaroof.onlinefoodtracker.Controllers;

import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserServices userServices;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Everything is working...";
    }

    //create a user
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        boolean createUser = userServices.saveNewUser(user);
        if(createUser){
        return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    //search a user
    @GetMapping("/search/name/{userName}")
    public ResponseEntity<User> searchOrder(@PathVariable String userName){
        User user = userServices.findByUserName(userName);
        if(user !=null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

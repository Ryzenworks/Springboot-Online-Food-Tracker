package com.mohammedmaroof.onlinefoodtracker.services;
import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserServices {



    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll(){
        log.debug("Fetching all users from repository");
        List<User> users = userRepository.findAll();
        log.info("Fetched {} users", users.size());
        return users;
    }



    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singletonList("USER"));
            userRepository.save(user);
            log.info("User '{}' created successfully", user.getUserName());
            return true;
        } catch (Exception e) {
            log.error("Username Already Exists for {}", user.getUserName(), e);
            return false;
        }
    }
    public void saveUser(User user){
        log.debug("Saving user '{}'", user.getUserName());
        userRepository.save(user);
        log.info("User '{}' saved", user.getUserName());
    }

    public void saveNewAdmin(User user){
        log.debug("Creating new admin user '{}'", user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
        log.info("Admin user '{}' created", user.getUserName());
    }



    public void deleteById(ObjectId objectId){
        log.debug("Deleting user with id '{}'", objectId);
        userRepository.deleteById(objectId);
        log.info("Deleted user with id '{}'", objectId);
    }

    public void deleteByUserName(String username){
        log.debug("Deleting user with username '{}'", username);
        userRepository.deleteByUserName(username);
        log.info("Deleted user '{}'", username);
    }

    public Optional<User> findById(ObjectId objectId){
        log.debug("Finding user by id '{}'", objectId);
        Optional<User> user = userRepository.findById(objectId);
        if(user.isPresent()){
            log.info("User found with id '{}'", objectId);
        } else {
            log.warn("No user found with id '{}'", objectId);
        }
        return user;
    }

    public User findByUserName(String userName){
        log.debug("Finding user by username '{}'", userName);
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            log.info("Found user '{}'", userName);
        } else {
            log.warn("User '{}' not found", userName);
        }
        return user;
    }

}

package com.mohammedmaroof.onlinefoodtracker.Controllers;


import com.mohammedmaroof.onlinefoodtracker.entity.Order;
import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.services.OrderServices;
import com.mohammedmaroof.onlinefoodtracker.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private UserServices userServices;

    //view all the user list
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> list = userServices.getAll();
        if(!list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update order status
    @PutMapping("/order-status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable ObjectId orderId,
            @RequestBody Order newOrder)
    {
        if (newOrder.getStatus() == null) {
            return new ResponseEntity<>("Status cannot be null", HttpStatus.BAD_REQUEST);
        }
        Optional<Order> optionalOrderEntry = orderServices.findById(orderId);

        if(optionalOrderEntry.isPresent()){
            Order oldOrder = optionalOrderEntry.get();
            oldOrder.setStatus(newOrder.getStatus());
            orderServices.saveOrder(oldOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-new-admin")
    public ResponseEntity<?> createNewAdmin(@RequestBody User user){
        userServices.saveNewAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}

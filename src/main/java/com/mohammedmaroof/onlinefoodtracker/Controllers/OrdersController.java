package com.mohammedmaroof.onlinefoodtracker.Controllers;

import com.mohammedmaroof.onlinefoodtracker.entity.Order;
import com.mohammedmaroof.onlinefoodtracker.entity.OrderStatus;
import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.services.OrderServices;
import com.mohammedmaroof.onlinefoodtracker.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private UserServices userServices;

    //view all the order list
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userServices.findByUserName(authentication.getName());
        List<Order> allOrders = user.getOrderEntries();
        if (allOrders != null && !allOrders.isEmpty()) {
            return new ResponseEntity<>(allOrders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //place order
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order myOrder){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            myOrder.setStatus(OrderStatus.PLACED);
            orderServices.saveOrder(myOrder, authentication.getName());
            return new ResponseEntity<>(myOrder, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get specific order by ID
    @GetMapping("/search/id/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable ObjectId orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userServices.findByUserName(authentication.getName());
        List<Order> orders = user.getOrderEntries().stream().filter(order -> order
                        .getOrderId()
                        .equals(orderId))
                        .collect(Collectors.toList());
        if(!orders.isEmpty()){
        Optional<Order> orderEntry = orderServices.findById(orderId);
            if(orderEntry.isPresent()){
                return new ResponseEntity<>(orderEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update order
    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrder(
            @PathVariable ObjectId orderId,
            @RequestBody Order newOrder)
    {
        if (newOrder.getStatus() != null) {
            return new ResponseEntity<>("Unauthorized to update order status", HttpStatus.FORBIDDEN);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userServices.findByUserName(authentication.getName());
        List<Order> orders = user.getOrderEntries().stream().filter(order -> order
                .getOrderId()
                .equals(orderId))
                .collect(Collectors.toList());
        if(!orders.isEmpty()){
        Optional<Order> optionalOrderEntry = orderServices.findById(orderId);
        if(optionalOrderEntry.isPresent()){
            Order oldOrder = optionalOrderEntry.get();
            oldOrder.setCustomerName(newOrder.getCustomerName() != null && newOrder.getCustomerName().isEmpty() ?
                    newOrder.getCustomerName() : oldOrder.getCustomerName());
            oldOrder.setItems(newOrder.getItems() != null && !newOrder.getItems().isEmpty() ?
                    newOrder.getItems(): oldOrder.getItems());
            orderServices.saveOrder(oldOrder);
            return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //cancel an order
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable ObjectId orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Order> orderEntry = orderServices.findById(orderId);
        if(orderEntry.isPresent()){
            orderServices.deleteById(orderId, authentication.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}



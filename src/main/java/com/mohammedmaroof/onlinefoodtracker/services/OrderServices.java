package com.mohammedmaroof.onlinefoodtracker.services;

import com.mohammedmaroof.onlinefoodtracker.entity.Order;
import com.mohammedmaroof.onlinefoodtracker.entity.User;
import com.mohammedmaroof.onlinefoodtracker.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//controllers -> service -> repository
@Component
@Slf4j
public class OrderServices {


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserServices userServices;


    @Transactional
    public void saveOrder(Order order, String userName){
        try {
            log.info("Placing new order for user {}", userName);
            User user = userServices.findByUserName(userName);
            order.setDate(LocalDateTime.now());
            Order savedOrder = ordersRepository.save(order);
            user.getOrderEntries().add(savedOrder);
            userServices.saveUser(user);
            log.info("Order {} saved successfully for user {}", savedOrder.getOrderId(), userName);
        } catch (Exception e){
            log.error("Failed to save order for {}", userName, e);
        }
    }
    @Transactional
    public void saveOrder(Order order){
        try {
            log.info("Saving order {} with status {}", order.getOrderId(), order.getStatus());
            order.setDate(LocalDateTime.now());
            ordersRepository.save(order);
            log.info("Order {} saved successfully with status {}", order.getOrderId(), order.getStatus());
        } catch (Exception e){
            log.error("Failed to save order {}", order.getOrderId(), e);
        }
    }

    public List<Order> getAllOrders(){
        log.debug("Fetching all orders");
        return ordersRepository.findAll();
    }

    public Optional<Order> findById(ObjectId myId){
        log.debug("Searching for order {}", myId);
        return ordersRepository.findById(myId);
    }

    @Transactional
    public void deleteById(ObjectId myId, String userName){
        try {
            log.info("Deleting order {} for user {}", myId, userName);
            User user = userServices.findByUserName(userName);
            boolean isRemoved = user
                    .getOrderEntries()
                    .removeIf(orderEntry -> orderEntry.getOrderId().equals(myId));
            if (isRemoved) {
                userServices.saveUser(user);
                ordersRepository.deleteById(myId);
                log.info("Order {} deleted successfully for user {}", myId, userName);
            } else {
                log.warn("Order {} not found in user {}'s entries", myId, userName);
            }
        } catch (Exception e) {
            log.error("Failed to delete order {} for user {}", myId, userName, e);
            throw new RuntimeException(e.getMessage());
        }
}
    public List<Order> findByName(String customerName){
        log.debug("Finding orders for customer {}", customerName);
        return ordersRepository.findByCustomerName(customerName);
    }
}


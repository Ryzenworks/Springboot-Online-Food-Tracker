package com.mohammedmaroof.onlinefoodtracker.repository;

import com.mohammedmaroof.onlinefoodtracker.entity.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersRepository extends MongoRepository<Order, ObjectId> {
    List<Order> findByCustomerName(String customerName);
}

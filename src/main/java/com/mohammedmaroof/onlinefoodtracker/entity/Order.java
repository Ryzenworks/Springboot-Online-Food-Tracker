package com.mohammedmaroof.onlinefoodtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "order_entries")
@Data
@NoArgsConstructor
public class Order {
    @Id
    private ObjectId orderId;

    private String customerName;

    private List<String> items;

    private OrderStatus status;

    private LocalDateTime date;
}

package com.micro.orderservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    private String productId;
    private String quantity;
    private String price;
    private String userId;
    private String status;
    private String paymentId;
    private String orderTime;
    private String deliveryTime;
    private String address;
}

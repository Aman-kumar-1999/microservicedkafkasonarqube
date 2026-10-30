package com.micro.orderservices.controller;


import com.micro.orderservices.dto.OrderDetailsDto;
import com.micro.orderservices.dto.PaymentEvent;
import com.micro.orderservices.model.OrderDetails;
import com.micro.orderservices.services.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServices orderServices;

    @PostMapping("/create")
    public ResponseEntity<OrderDetails> createOrder(@RequestBody OrderDetailsDto orderDetailsDto) {
        return ResponseEntity.ok(orderServices.createOrder(orderDetailsDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody PaymentEvent paymentEvent) {
        return ResponseEntity.ok(orderServices.updateOrder(paymentEvent));
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable String orderId) {
        return ResponseEntity.ok(orderServices.getOrderDetails(orderId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDetails>> getAllOrders() {
        return ResponseEntity.ok(orderServices.getAllOrders());
    }





}

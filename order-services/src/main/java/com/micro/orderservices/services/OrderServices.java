package com.micro.orderservices.services;

import com.micro.orderservices.config.KafkaTopicConfig;
import com.micro.orderservices.dto.OrderDetailsDto;
import com.micro.orderservices.dto.PaymentEvent;
import com.micro.orderservices.model.OrderDetails;
import com.micro.orderservices.repo.OrderDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServices {

    private final OrderDetailsRepo orderDetailsRepo;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public OrderDetails createOrder(OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = orderDetailsDto.convertDtoToOrderDetails(orderDetailsDto);
        orderDetails.setStatus("PENDING");
        orderDetailsRepo.save(orderDetails);
        return  orderDetails;
    }

    public OrderDetails getOrderDetails(String orderId) {
        return orderDetailsRepo.findById(orderId).orElse(null);
    }

    public void deleteOrder(String orderId) {
        orderDetailsRepo.deleteById(orderId);
    }

    public List<OrderDetails> getAllOrders() {
        return orderDetailsRepo.findAll();
    }

    public String updateOrder(PaymentEvent paymentEvent) {
            kafkaTemplate.send(KafkaTopicConfig.PAYMENT_TOPIC, paymentEvent);
        return "Order updated successfully wait for few minutes then your order will be processed";
    }

    public void deleteAllOrders() {
        orderDetailsRepo.deleteAll();
    }
}

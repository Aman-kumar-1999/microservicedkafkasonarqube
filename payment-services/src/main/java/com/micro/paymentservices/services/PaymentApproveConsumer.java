package com.micro.paymentservices.services;


import com.micro.paymentservices.dto.PaymentEvent;
import com.micro.paymentservices.model.OrderDetails;
import com.micro.paymentservices.repo.OrderDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApproveConsumer {

    private final OrderDetailsRepo orderDetailsRepo;

    @KafkaListener(topics = "order.payments.initiated", groupId = "payment-group")
    public void consume(PaymentEvent paymentEvent) {
        OrderDetails orderDetails = orderDetailsRepo.findById(paymentEvent.getOrderId()).orElse(null);
        if (orderDetails != null) {
            if (paymentEvent.getStatus().equalsIgnoreCase("PENDING")) {
                orderDetails.setStatus("PENDING");
            } else if (paymentEvent.getStatus().equalsIgnoreCase("SUCCESS")) {
                orderDetails.setStatus("SUCCESS");
            } else if (paymentEvent.getStatus().equalsIgnoreCase("DECLINED")) {
                orderDetails.setStatus("DECLINED");
            } else {
                orderDetails.setStatus("FAILURE");
            }
            orderDetailsRepo.save(orderDetails);
        }
    }
}

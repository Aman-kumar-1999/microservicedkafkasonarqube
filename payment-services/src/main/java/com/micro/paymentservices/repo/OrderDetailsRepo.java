package com.micro.paymentservices.repo;

import com.micro.paymentservices.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, String> {
}

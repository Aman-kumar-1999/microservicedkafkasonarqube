package com.micro.orderservices.repo;

import com.micro.orderservices.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, String> {
}

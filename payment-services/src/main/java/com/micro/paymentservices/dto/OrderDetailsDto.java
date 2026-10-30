package com.micro.paymentservices.dto;

import com.micro.paymentservices.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

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

    public OrderDetailsDto convertOrderDetailsToDto(OrderDetails orderDetails) {
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setOrderId(orderDetails.getOrderId());
        orderDetailsDto.setProductId(orderDetails.getProductId());
        orderDetailsDto.setQuantity(orderDetails.getQuantity());
        orderDetailsDto.setPrice(orderDetails.getPrice());
        orderDetailsDto.setUserId(orderDetails.getUserId());
        orderDetailsDto.setStatus(orderDetails.getStatus());
        orderDetailsDto.setPaymentId(orderDetails.getPaymentId());
        orderDetailsDto.setOrderTime(orderDetails.getOrderTime());
        orderDetailsDto.setDeliveryTime(orderDetails.getDeliveryTime());
        orderDetailsDto.setAddress(orderDetails.getAddress());
        return orderDetailsDto;
    }
    public OrderDetails convertDtoToOrderDetails() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderDetails.setProductId(productId);
        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(price);
        orderDetails.setUserId(userId);
        orderDetails.setStatus(status);
        orderDetails.setPaymentId(paymentId);
        orderDetails.setOrderTime(orderTime);
        orderDetails.setDeliveryTime(deliveryTime);
        orderDetails.setAddress(address);
        return orderDetails;

    }


}

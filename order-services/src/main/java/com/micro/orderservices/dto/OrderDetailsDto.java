package com.micro.orderservices.dto;

import com.micro.orderservices.model.OrderDetails;
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
    public OrderDetails convertDtoToOrderDetails(OrderDetailsDto orderDetailsDto) {
        if (orderDetailsDto == null) {
            return null; // Or handle as per your application's error handling strategy
        }

        OrderDetails orderDetails = new OrderDetails();

//        orderDetails.setOrderId(orderDetailsDto.getOrderId() == null ? "" : orderDetailsDto.getOrderId());
        orderDetails.setProductId(orderDetailsDto.getProductId() == null ? "" : orderDetailsDto.getProductId());
        orderDetails.setUserId(orderDetailsDto.getUserId() == null ? "" : orderDetailsDto.getUserId());
        orderDetails.setStatus(orderDetailsDto.getStatus() == null ? "" : orderDetailsDto.getStatus());
        orderDetails.setPaymentId(orderDetailsDto.getPaymentId() == null ? "" : orderDetailsDto.getPaymentId());
        orderDetails.setOrderTime(orderDetailsDto.getOrderTime() == null ? "" : orderDetailsDto.getOrderTime());
        orderDetails.setDeliveryTime(orderDetailsDto.getDeliveryTime() == null ? "" : orderDetailsDto.getDeliveryTime());
        orderDetails.setAddress(orderDetailsDto.getAddress() == null ? "" : orderDetailsDto.getAddress());


        orderDetails.setQuantity(orderDetailsDto.getQuantity() == null ? "" : orderDetailsDto.getQuantity());
        orderDetails.setPrice(orderDetailsDto.getPrice() == null ? "" : orderDetailsDto.getPrice());

        return orderDetails;
    }


}

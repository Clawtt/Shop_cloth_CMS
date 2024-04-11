package com.shop_Karol_Herzog_Banasik.order.services;

import com.shop_Karol_Herzog_Banasik.order.dto.OrderRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addNewOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders(int pageSize);

    Optional<OrderResponseDto> findById(Long id);

    void completeOrder(Long id, boolean isCompleted);


}

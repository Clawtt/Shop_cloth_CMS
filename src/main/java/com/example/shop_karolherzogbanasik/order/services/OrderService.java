package com.example.shop_karolherzogbanasik.order.services;

import com.example.shop_karolherzogbanasik.order.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.order.dto.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addNewOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders(int pageSize);

    Optional<OrderResponseDto> findById(Long id);

    void completeOrder(Long id, boolean isCompleted);


}

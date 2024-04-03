package com.example.shop_karolherzogbanasik.customer.services;

import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addNewOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders(int pageSize);

    Optional<OrderResponseDto> findById(Long id);


}

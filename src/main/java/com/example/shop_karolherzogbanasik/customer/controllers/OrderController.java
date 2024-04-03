package com.example.shop_karolherzogbanasik.customer.controllers;

import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.customer.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("order/add")
    public ResponseEntity<OrderRequestDto> addNewOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.addNewOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

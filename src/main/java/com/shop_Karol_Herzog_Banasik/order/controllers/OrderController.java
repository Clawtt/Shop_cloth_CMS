package com.shop_Karol_Herzog_Banasik.order.controllers;

import com.shop_Karol_Herzog_Banasik.order.dto.OrderRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderResponseDto;
import com.shop_Karol_Herzog_Banasik.order.services.OrderService;
import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("order/add")
    public ResponseEntity<OrderRequestDto> addNewOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.addNewOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@RequestParam(
            name = "page", required = false, defaultValue = "10") int page) {
        List<OrderResponseDto> allOrders = orderService.getAllOrders(page);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = orderService.findById(id).orElseThrow(
                () -> new NoElementFoundException("order with %d id doesn't exist".formatted(id)));
        if (orderService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @PatchMapping("/order/{id}")
    public ResponseEntity<OrderResponseDto> completeOrder(@PathVariable Long id,
                                                          @RequestParam(required = true, defaultValue = "true", name = "completed") String isCompleted) {
        boolean isCompletedAsBoolean = Boolean.parseBoolean(isCompleted);
        orderService.completeOrder(id, isCompletedAsBoolean);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

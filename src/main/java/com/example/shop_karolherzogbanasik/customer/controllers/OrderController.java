package com.example.shop_karolherzogbanasik.customer.controllers;

import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderResponseDto;
import com.example.shop_karolherzogbanasik.customer.services.OrderService;
import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoElementFoundException.class)
    Map<String, String> handleNoElementFoundException(NoElementFoundException exception) {
        HashMap<String, String> exMap = new HashMap<>();
        exMap.put("message", exception.getMessage());
        return exMap;
    }
}

package com.example.shop_karolherzogbanasik.customer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {



//    @PostMapping(value = "/customer/add")
//    public ResponseEntity<CustomerRequestDto> addNewCustomerOrUpdate(@RequestBody CustomerRequestDto customerDto) {
//        customerService.addNewCustomer(customerDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/customer")
//    public ResponseEntity<CustomerRequestDto> findByEmail(@RequestParam String email) {
//        CustomerRequestDto customerDto = customerService.findByEmail(email).orElseThrow(() -> new NoElementFoundException("customer with email: %s doesn't exists".formatted(email)));
//        return ResponseEntity.ok(customerDto);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(NoElementFoundException.class)
//    Map<String, String> handleNoElementFoundEx (NoElementFoundException exception) {
//        HashMap<String, String> exMap = new HashMap<>();
//        exMap.put("message", exception.getMessage());
//        return exMap;
//    }
}

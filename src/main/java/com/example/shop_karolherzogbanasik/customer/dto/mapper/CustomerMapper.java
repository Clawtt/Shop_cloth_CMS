package com.example.shop_karolherzogbanasik.customer.dto.mapper;

import com.example.shop_karolherzogbanasik.customer.Customer;
import com.example.shop_karolherzogbanasik.customer.dto.CustomerRequestDto;

public class CustomerMapper {

    public static CustomerRequestDto map(Customer customer) {
        return new CustomerRequestDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
    }
}

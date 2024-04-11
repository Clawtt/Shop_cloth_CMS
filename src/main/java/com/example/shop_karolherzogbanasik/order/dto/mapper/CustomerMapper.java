package com.example.shop_karolherzogbanasik.order.dto.mapper;

import com.example.shop_karolherzogbanasik.order.Customer;
import com.example.shop_karolherzogbanasik.order.dto.CustomerDto;

public class CustomerMapper {

    public static CustomerDto map(Customer customer) {
        return new CustomerDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
    }
}

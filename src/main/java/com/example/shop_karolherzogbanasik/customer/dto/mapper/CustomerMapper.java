package com.example.shop_karolherzogbanasik.customer.dto.mapper;

import com.example.shop_karolherzogbanasik.customer.Customer;
import com.example.shop_karolherzogbanasik.customer.dto.CustomerDto;

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

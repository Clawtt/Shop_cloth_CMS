package com.shop_Karol_Herzog_Banasik.order.dto.mapper;

import com.shop_Karol_Herzog_Banasik.order.Customer;
import com.shop_Karol_Herzog_Banasik.order.dto.CustomerRequestDto;

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

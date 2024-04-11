package com.shop_Karol_Herzog_Banasik.order.dto.mapper;

import com.shop_Karol_Herzog_Banasik.order.Customer;
import com.shop_Karol_Herzog_Banasik.order.dto.CustomerDto;

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

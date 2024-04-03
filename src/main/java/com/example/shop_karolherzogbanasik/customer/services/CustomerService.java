package com.example.shop_karolherzogbanasik.customer.services;

import com.example.shop_karolherzogbanasik.customer.dto.CustomerRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;

import java.util.Optional;

public interface CustomerService {

    Optional<CustomerRequestDto> getByEmail(String email);

}

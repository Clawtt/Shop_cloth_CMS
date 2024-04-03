package com.example.shop_karolherzogbanasik.customer.services;

import com.example.shop_karolherzogbanasik.customer.dto.CustomerRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.mapper.CustomerMapper;
import com.example.shop_karolherzogbanasik.customer.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Override
    public Optional<CustomerRequestDto> getByEmail(String email) {
        return customerRepository.findByEmail(email).map(CustomerMapper::map);
    }
}

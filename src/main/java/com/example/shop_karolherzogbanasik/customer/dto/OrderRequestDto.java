package com.example.shop_karolherzogbanasik.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDto {


    private CustomerRequestDto customer;

    private List<AddressRequestDto> addresses;

    private List<Long> productIds;


}

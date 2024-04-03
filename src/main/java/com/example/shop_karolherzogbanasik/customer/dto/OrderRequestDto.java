package com.example.shop_karolherzogbanasik.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@NoArgsConstructor
@Data
public class OrderRequestDto {


    private CustomerDto customer;

    private List<AddressDto> addresses;

    private List<Long> productIds;


}

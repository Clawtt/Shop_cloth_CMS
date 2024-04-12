package com.shop_Karol_Herzog_Banasik.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDto {


    private CustomerRequestDto customer;

    private boolean completed = false;

    private List<AddressDto> addresses;

    private List<Long> productIds;


}

package com.shop_Karol_Herzog_Banasik.order.dto;

import com.shop_Karol_Herzog_Banasik.order.customer.dto.CustomerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderRequestDto {


    private CustomerRequestDto customer;

    private List<Long> productIds;


}

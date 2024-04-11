package com.shop_Karol_Herzog_Banasik.order.dto;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDto {


    private CustomerDto customer;
    private LocalDateTime createdAt = LocalDateTimeProvider.getCurrentTime();
    private boolean completed = false;

    private List<AddressDto> addresses;

    private List<Long> productIds;


}

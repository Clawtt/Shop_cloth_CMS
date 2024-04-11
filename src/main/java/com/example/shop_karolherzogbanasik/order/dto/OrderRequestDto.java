package com.example.shop_karolherzogbanasik.order.dto;

import com.example.shop_karolherzogbanasik.LocalDateTimeProvider;
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

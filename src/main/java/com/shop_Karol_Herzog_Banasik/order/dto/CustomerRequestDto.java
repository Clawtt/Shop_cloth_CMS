package com.shop_Karol_Herzog_Banasik.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;
    

}

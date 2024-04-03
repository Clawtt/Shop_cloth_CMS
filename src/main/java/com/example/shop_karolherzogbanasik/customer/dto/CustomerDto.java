package com.example.shop_karolherzogbanasik.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;
    

}

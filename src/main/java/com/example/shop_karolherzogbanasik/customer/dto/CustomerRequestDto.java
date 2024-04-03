package com.example.shop_karolherzogbanasik.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;
    

}

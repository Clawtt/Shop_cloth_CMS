package com.example.shop_karolherzogbanasik.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {

    private boolean shippingAddress;

    private boolean invoiceAddress;

    private String street;

    private String streetNo;

    private String zipCode;

    private String city;


}
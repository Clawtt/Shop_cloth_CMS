package com.shop_Karol_Herzog_Banasik.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private boolean shippingAddress;

    private boolean invoiceAddress;

    private String street;

    private String streetNo;

    private String zipCode;

    private String city;


}

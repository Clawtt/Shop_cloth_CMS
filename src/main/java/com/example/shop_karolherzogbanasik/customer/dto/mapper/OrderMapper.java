package com.example.shop_karolherzogbanasik.customer.dto.mapper;

import com.example.shop_karolherzogbanasik.customer.Address;
import com.example.shop_karolherzogbanasik.customer.OrderApp;
import com.example.shop_karolherzogbanasik.customer.dto.AddressDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderResponseDto;
import com.example.shop_karolherzogbanasik.product.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderResponseDto map(OrderApp order) {
        List<Product> products = new ArrayList<>(order.getProducts());
        List<AddressDto> addresses = mapAddress(order);
        return new OrderResponseDto(
                order.getId(),
                order.getCreatedAt(),
                order.getCompletedAt(),
                order.isCompleted(),
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                order.getCustomer().getPhoneNumber(),
                order.getCustomer().getEmail(),
                addresses,
                products
        );
    }
    private static List<AddressDto> mapAddress(OrderApp order) {
        List<AddressDto> addresses = new ArrayList<>();
        for (Address address : order.getCustomer().getAddresses()) {
            AddressDto addressDto = new AddressDto();
            addressDto.setShippingAddress(address.isShippingAddress());
            addressDto.setInvoiceAddress(address.isInvoiceAddress());
            addressDto.setStreet(address.getStreet());
            addressDto.setStreetNo(address.getStreetNo());
            addressDto.setStreetNo(address.getZipCode());
            addressDto.setStreetNo(address.getCity());
            addresses.add(addressDto);
        }
        return addresses;
    }
}

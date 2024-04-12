package com.shop_Karol_Herzog_Banasik.order.dto.mapper;

import com.shop_Karol_Herzog_Banasik.order.OrderApp;
import com.shop_Karol_Herzog_Banasik.order.dto.AddressDto;
import com.shop_Karol_Herzog_Banasik.order.dto.CustomerRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.CustomerResponseDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderResponseDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto map(OrderApp order) {
        List<ProductDto> products = order.getProducts()
                .stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getTypes().stream()
                                    .map(productType -> new ProductTypeDto(productType.getId(), productType.getName()))
                                    .collect(Collectors.toList()),
                            product.getDiscountPrice());
                    return productDto;
                }).collect(Collectors.toList());

        return new OrderResponseDto(
                order.getId(),
                order.getCreatedAt(),
                order.getCompletedAt(),
                order.isCompleted(),
                mapCustomer(order),
                products
        );
    }

    private static CustomerResponseDto mapCustomer(OrderApp order) {
        CustomerResponseDto customerDto = new CustomerResponseDto();
        customerDto.setId(order.getCustomer().getId());
        customerDto.setFirstName(order.getCustomer().getFirstName());
        customerDto.setLastName(order.getCustomer().getLastName());
        customerDto.setPhoneNumber(order.getCustomer().getPhoneNumber());
        customerDto.setEmail(order.getCustomer().getEmail());
        customerDto.setAddresses(order.getCustomer().getAddresses()
                .stream()
                .map(address -> new AddressDto(
                        address.isShippingAddress(),
                        address.isInvoiceAddress(),
                        address.getStreet(),
                        address.getStreetNo(),
                        address.getZipCode(),
                        address.getCity()
                )).collect(Collectors.toList()));
        return customerDto;
    }
}

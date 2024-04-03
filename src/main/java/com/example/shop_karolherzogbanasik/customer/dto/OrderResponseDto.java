package com.example.shop_karolherzogbanasik.customer.dto;

import com.example.shop_karolherzogbanasik.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private boolean completed;

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;

    List<AddressDto> addresses;

    private List<Product> products;

}

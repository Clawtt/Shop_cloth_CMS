package com.shop_Karol_Herzog_Banasik.order.dto;

import com.shop_Karol_Herzog_Banasik.product.Product;
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

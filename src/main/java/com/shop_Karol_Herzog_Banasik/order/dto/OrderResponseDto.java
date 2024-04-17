package com.shop_Karol_Herzog_Banasik.order.dto;

import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private boolean completed;

    private CustomerResponseDto customerResponseDto;

    private List<ProductDto> products;

}

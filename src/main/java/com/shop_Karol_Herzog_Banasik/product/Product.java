package com.shop_Karol_Herzog_Banasik.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private BigDecimal discountPrice;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "products_types",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))

    private List<ProductType> types;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private List<Image> images = new ArrayList<>();
}

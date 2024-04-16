package com.shop_Karol_Herzog_Banasik.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

}

package com.example.shop_karolherzogbanasik.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean shippingAddress;
    private boolean invoiceAddress;
    private String street;
    private String streetNo;
    private String zipCode;
    private String city;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;


}

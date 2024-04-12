package com.shop_Karol_Herzog_Banasik.order;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List <Address> addresses = new ArrayList<>();

    public void addAddress(Address address) {
        addresses.add(address);
    }

}

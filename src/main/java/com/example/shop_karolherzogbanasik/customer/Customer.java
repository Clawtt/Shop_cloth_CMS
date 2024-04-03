package com.example.shop_karolherzogbanasik.customer;

import com.thoughtworks.qdox.model.expression.Add;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List <Address> addresses = new ArrayList<>();

    public void addAddress(Address address) {
        addresses.add(address);
    }
}

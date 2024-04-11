package com.example.shop_karolherzogbanasik.order;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }
}

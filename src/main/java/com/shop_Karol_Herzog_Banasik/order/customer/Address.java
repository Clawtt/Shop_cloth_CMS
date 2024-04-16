package com.shop_Karol_Herzog_Banasik.order.customer;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return shippingAddress == address.shippingAddress && invoiceAddress == address.invoiceAddress && Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(streetNo, address.streetNo) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shippingAddress, invoiceAddress, street, streetNo, zipCode, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", shippingAddress=" + shippingAddress +
                ", invoiceAddress=" + invoiceAddress +
                ", street='" + street + '\'' +
                ", streetNo='" + streetNo + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

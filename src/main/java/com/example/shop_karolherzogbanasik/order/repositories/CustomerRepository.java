package com.example.shop_karolherzogbanasik.order.repositories;

import com.example.shop_karolherzogbanasik.order.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @NonNull
    Optional<Customer> findByEmail(@NonNull String email);


}

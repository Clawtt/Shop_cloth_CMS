package com.example.shop_karolherzogbanasik.customer.repositories;

import com.example.shop_karolherzogbanasik.customer.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @NonNull
    Optional<Customer> findByEmail(@NonNull String email);


}

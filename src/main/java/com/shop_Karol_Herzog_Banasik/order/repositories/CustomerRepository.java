package com.shop_Karol_Herzog_Banasik.order.repositories;

import com.shop_Karol_Herzog_Banasik.order.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @NonNull
    Optional<Customer> findByEmail(@NonNull String email);


}

package com.shop_Karol_Herzog_Banasik.order.repositories;

import com.shop_Karol_Herzog_Banasik.order.customer.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findById(Long id);
}

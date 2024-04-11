package com.example.shop_karolherzogbanasik.order.repositories;

import com.example.shop_karolherzogbanasik.order.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findById(Long id);
}

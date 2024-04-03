package com.example.shop_karolherzogbanasik.customer.repositories;

import com.example.shop_karolherzogbanasik.customer.OrderApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderApp, Long> {



}

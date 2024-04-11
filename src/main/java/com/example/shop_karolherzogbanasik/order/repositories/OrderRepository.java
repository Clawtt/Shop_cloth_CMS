package com.example.shop_karolherzogbanasik.order.repositories;

import com.example.shop_karolherzogbanasik.order.OrderApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderApp, Long> {



}

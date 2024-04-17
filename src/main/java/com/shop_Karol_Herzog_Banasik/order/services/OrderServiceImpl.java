package com.shop_Karol_Herzog_Banasik.order.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.order.Address;
import com.shop_Karol_Herzog_Banasik.order.Customer;
import com.shop_Karol_Herzog_Banasik.order.OrderApp;
import com.shop_Karol_Herzog_Banasik.order.dto.AddressDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderResponseDto;
import com.shop_Karol_Herzog_Banasik.order.dto.mapper.OrderMapper;
import com.shop_Karol_Herzog_Banasik.order.repositories.AddressRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.CustomerRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.OrderRepository;
import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final LocalDateTimeProvider currentTime;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;


    @Transactional
    @Override
    public void addNewOrder(OrderRequestDto orderRequestDto) {
        OrderApp order = new OrderApp();
        Customer customer = setCustomerWithAddresses(orderRequestDto);
        List<Long> productIds = orderRequestDto.getProductIds();
        for (Long id : productIds) {
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new NoElementFoundException("product with %d id doesn't exist".formatted(id)));
            order.addProduct(product);
        }
        order.setCreatedAt(currentTime.currentTime());
        order.setCompleted(false);
        order.setCustomer(customer);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(int pageSize) {
        Pageable page = Pageable.ofSize(pageSize);
        return orderRepository.findAll(page)
                .stream()
                .map(OrderMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponseDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::map);
    }

    @Transactional
    @Override
    public void completeOrder(Long id, boolean isCompleted) {
        OrderApp orderApp = orderRepository.findById(id).orElseThrow(
                () -> new NoElementFoundException("order with %d id doesn't exist".formatted(id)));
        orderApp.setCompleted(isCompleted);
        orderApp.setCompletedAt(currentTime.currentTime());
        orderRepository.save(orderApp);
    }

    private Customer setCustomerWithAddresses(OrderRequestDto orderRequestDto) {
        Customer customer = new Customer();
        for (AddressDto addressRequestDto : new ArrayList<>(
                orderRequestDto.getCustomer().getAddresses())) {
            Address address = new Address();
            address.setShippingAddress(addressRequestDto.isShippingAddress());
            address.setInvoiceAddress(addressRequestDto.isInvoiceAddress());
            address.setStreet(addressRequestDto.getStreet());
            address.setStreetNo(addressRequestDto.getStreetNo());
            address.setZipCode(addressRequestDto.getZipCode());
            address.setCity(addressRequestDto.getCity());
            address.setCustomer(customer);
            customer.addAddress(address);
            addressRepository.save(address);
            customer.setFirstName(orderRequestDto.getCustomer().getFirstName());
            customer.setLastName(orderRequestDto.getCustomer().getLastName());
            customer.setPhoneNumber(orderRequestDto.getCustomer().getPhoneNumber());
            customer.setEmail(orderRequestDto.getCustomer().getEmail());
            customerRepository.save(customer);
        }
        return customer;
    }
}


package com.example.shop_karolherzogbanasik.order.services;

import com.example.shop_karolherzogbanasik.order.Address;
import com.example.shop_karolherzogbanasik.order.Customer;
import com.example.shop_karolherzogbanasik.order.OrderApp;
import com.example.shop_karolherzogbanasik.order.dto.AddressDto;
import com.example.shop_karolherzogbanasik.order.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.order.dto.OrderResponseDto;
import com.example.shop_karolherzogbanasik.order.dto.mapper.OrderMapper;
import com.example.shop_karolherzogbanasik.order.repositories.AddressRepository;
import com.example.shop_karolherzogbanasik.order.repositories.CustomerRepository;
import com.example.shop_karolherzogbanasik.order.repositories.OrderRepository;
import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductService productService;


    @Transactional
    @Override
    public void addNewOrder(OrderRequestDto orderRequestDto) {
        OrderApp order = new OrderApp();
        Customer customer = configureCustomerWithAddress(orderRequestDto);
        List<Long> productIds = orderRequestDto.getProductIds();
        for (Long id : productIds) {
            Product product = productService.findProductById(id).orElseThrow(
                    () -> new NoElementFoundException("product with %d id doesn't exist".formatted(id)));
            order.addProduct(product);
        }
        order.setCreatedAt(orderRequestDto.getCreatedAt());
        order.setCompleted(orderRequestDto.isCompleted());
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
        orderApp.setCompletedAt(LocalDateTime.now());
    }

    private Customer configureCustomerWithAddress(OrderRequestDto orderRequestDto) {
        Customer customer = new Customer();
        for (AddressDto addressRequestDto : new ArrayList<>(orderRequestDto.getAddresses())) {
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


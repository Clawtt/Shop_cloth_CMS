package com.example.shop_karolherzogbanasik.customer.services;

import com.example.shop_karolherzogbanasik.customer.Address;
import com.example.shop_karolherzogbanasik.customer.Customer;
import com.example.shop_karolherzogbanasik.customer.OrderApp;
import com.example.shop_karolherzogbanasik.customer.dto.AddressDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderResponseDto;
import com.example.shop_karolherzogbanasik.customer.dto.mapper.OrderMapper;
import com.example.shop_karolherzogbanasik.customer.repositories.AddressRepository;
import com.example.shop_karolherzogbanasik.customer.repositories.CustomerRepository;
import com.example.shop_karolherzogbanasik.customer.repositories.OrderRepository;
import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.services.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductService productService;
    private final LocalDateTime CREATED_AT_NOW;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CustomerRepository customerRepository, ProductService productService,
            AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
        this.addressRepository = addressRepository;
        this.CREATED_AT_NOW = LocalDateTime.now();
    }

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
        order.setCreatedAt(CREATED_AT_NOW);
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


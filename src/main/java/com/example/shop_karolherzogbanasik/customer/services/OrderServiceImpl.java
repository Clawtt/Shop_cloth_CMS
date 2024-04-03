package com.example.shop_karolherzogbanasik.customer.services;

import com.example.shop_karolherzogbanasik.customer.Address;
import com.example.shop_karolherzogbanasik.customer.Customer;
import com.example.shop_karolherzogbanasik.customer.OrderApp;
import com.example.shop_karolherzogbanasik.customer.dto.AddressRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.CustomerRequestDto;
import com.example.shop_karolherzogbanasik.customer.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.customer.repositories.AddressRepository;
import com.example.shop_karolherzogbanasik.customer.repositories.OrderRepository;
import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final AddressRepository addressRepository;
    private final LocalDateTime CREATED_AT_NOW;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductService productService,
            AddressRepository addressRepository)
    {
        this.orderRepository = orderRepository;
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
        order.addCustomer(customer);
        orderRepository.save(order);
    }

    private Customer configureCustomerWithAddress(OrderRequestDto orderRequestDto) {
        Customer customer = new Customer();
        for (AddressRequestDto addressRequestDto : new ArrayList<>(orderRequestDto.getAddresses())) {
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
        }
        return customer;
    }
}


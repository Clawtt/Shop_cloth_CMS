package com.example.shop_karolherzogbanasik.order.services;

import com.example.shop_karolherzogbanasik.LocalDateTimeProvider;
import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.order.Address;
import com.example.shop_karolherzogbanasik.order.Customer;
import com.example.shop_karolherzogbanasik.order.OrderApp;
import com.example.shop_karolherzogbanasik.order.dto.AddressDto;
import com.example.shop_karolherzogbanasik.order.dto.CustomerDto;
import com.example.shop_karolherzogbanasik.order.dto.OrderRequestDto;
import com.example.shop_karolherzogbanasik.order.repositories.AddressRepository;
import com.example.shop_karolherzogbanasik.order.repositories.CustomerRepository;
import com.example.shop_karolherzogbanasik.order.repositories.OrderRepository;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.ProductType;
import com.example.shop_karolherzogbanasik.product.services.ProductService;
import com.example.shop_karolherzogbanasik.product.services.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    private OrderRequestDto orderRequestDto;
    private CustomerDto customerDto;
    private AddressDto addressDto;
    private Address address;
    private Product product;
    private Customer customer;
    private OrderApp order;

    public OrderServiceTest() {
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        orderService = new OrderServiceImpl(
                orderRepository,
                customerRepository,
                addressRepository,
                productService);
        ProductType productType = new ProductType(1L, "type");
        customerDto = new CustomerDto(
                "Adam",
                "Smith",
                222333444,
                "adam@smith.eu"
        );
        customer =  Customer.builder()
                .id(null)
                .firstName("Adam")
                .lastName("Smith")
                .phoneNumber(222333444)
                .email("adam@smith.eu")
                .build();
        addressDto = new AddressDto(
                true,
                true,
                "street",
                "12",
                "22-222",
                "city"
        );
        address = Address.builder()
                .id(null)
                .invoiceAddress(true)
                .shippingAddress(true)
                .street("street")
                .streetNo("12")
                .zipCode("22-222")
                .city("city")
                .build();
        product = new Product(
                1L,
                "iphone",
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1500),
                List.of(productType)
        );
        orderRequestDto = new OrderRequestDto(
                customerDto,
                LocalDateTimeProvider.getCurrentTime(),
                false,
                List.of(addressDto),
                List.of(product.getId())
        );
        order = OrderApp.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .completed(false)
                .products(List.of(product))
                .build();
    }

    @Nested
    public class addNewOrder_Method {

        @Test
        public void addNewOrder() {
            //given
            long empty = 0L;
            when(productService.findProductById(1L)).thenReturn(Optional.of(product));
            when(addressRepository.save(address)).thenReturn(address);
            when(customerRepository.save(customer)).thenReturn(customer);
            when(orderRepository.save(order)).thenReturn(order);
            when(productService.findProductById(empty))
                    .thenThrow(new NoElementFoundException("product with %d id doesn't exist".formatted(empty)));
            //when
            orderService.addNewOrder(orderRequestDto);
            //then
            Mockito.verify(addressRepository).save(address);
            Mockito.verify(customerRepository).save(customer);
            Assertions.assertThatThrownBy(() -> productService.findProductById(empty))
                    .isInstanceOf(NoElementFoundException.class)
                    .hasMessage("product with %d id doesn't exist".formatted(empty));
            Mockito.verify(orderRepository).save(order);
        }

        @Test
        public void shouldSetCreationAtFieldToCurrentDate() {
            //given
            LocalDateTime now = LocalDateTime.now();
            //when
            //then
            assertThat(order.getCreatedAt().isEqual(now));
        }


    }


}

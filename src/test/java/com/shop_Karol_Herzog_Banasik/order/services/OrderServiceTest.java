package com.shop_Karol_Herzog_Banasik.order.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.order.Address;
import com.shop_Karol_Herzog_Banasik.order.Customer;
import com.shop_Karol_Herzog_Banasik.order.OrderApp;
import com.shop_Karol_Herzog_Banasik.order.dto.*;
import com.shop_Karol_Herzog_Banasik.order.repositories.AddressRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.CustomerRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.OrderRepository;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;
import com.shop_Karol_Herzog_Banasik.product.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private AddressRepository addressRepositoryMock;
    @Mock
    private ProductService productServiceMock;
    @Mock
    private OrderService orderServiceMock;
    @Mock
    LocalDateTimeProvider localDateTimeProviderMock;


    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private CustomerRequestDto customerDto;
    private AddressDto addressDto;
    private Address address;
    private Product product;
    private Customer customer;
    private OrderApp order;
    private OrderServiceImpl orderService;
    private ProductDto productDto;
    private ProductTypeDto productTypeDto;
    private CustomerResponseDto customerResponseDto;


    public OrderServiceTest() {
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        List<Address> addresses = List.of(
                Address.builder()
                        .id(1L)
                        .invoiceAddress(true)
                        .shippingAddress(true)
                        .street("street")
                        .streetNo("12")
                        .zipCode("22-222")
                        .city("city")
                        .build()
        );
        when(localDateTimeProviderMock.currentTime())
                .thenReturn(LocalDateTime.of(2024, 4, 12, 12, 30, 30));
        orderService = new OrderServiceImpl(
                localDateTimeProviderMock,
                orderRepositoryMock,
                customerRepositoryMock,
                addressRepositoryMock,
                productServiceMock
        );

        orderServiceMock = new OrderServiceImpl(
                localDateTimeProviderMock,
                orderRepositoryMock,
                customerRepositoryMock,
                addressRepositoryMock,
                productServiceMock);

        ProductType productType = new ProductType(1L, "type");

        customerDto = new CustomerRequestDto(
                "Adam",
                "Smith",
                222333444,
                "adam@smith.eu"
        );
        addressDto = new AddressDto(
                true,
                true,
                "street",
                "12",
                "22-222",
                "city"
        );
        address = Address.builder()
                .id(1L)
                .invoiceAddress(true)
                .shippingAddress(true)
                .street("street")
                .streetNo("12")
                .zipCode("22-222")
                .city("city")
                .build();

        customer = Customer.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Smith")
                .phoneNumber(222333444)
                .email("adam@smith.eu")
                .addresses(addresses)
                .build();

        product = new Product(
                1L,
                "T-shirt",
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1500),
                List.of(productType)
        );
        productTypeDto = ProductTypeDto.builder()
                .id(1L)
                .name("man")
                .build();

        productDto = ProductDto.builder()
                .id(1L)
                .name("T-Shirt")
                .price(BigDecimal.valueOf(200))
                .discountPrice(BigDecimal.valueOf(100))
                .types(List.of(productTypeDto))
                .build();


        orderRequestDto = new OrderRequestDto(
                customerDto,
                false,
                List.of(addressDto),
                List.of(product.getId())
        );
        customerResponseDto = CustomerResponseDto.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Smith")
                .phoneNumber(222333444)
                .email("adam@smith.eu")
                .addresses(List.of(addressDto))
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .id(1L)
                .createdAt(localDateTimeProviderMock.currentTime())
                .completedAt(localDateTimeProviderMock.currentTime().plusHours(5))
                .completed(true)
                .customerResponseDto(customerResponseDto)
                .build();

        order = OrderApp.builder()
                .id(1L)
                .customer(customer)
                .createdAt(localDateTimeProviderMock.currentTime())
                .completed(false)
                .products(List.of(product))
                .build();
    }

    @Test
    @DisplayName("Should add new order and throw an exception when product id is empty")
    public void addNewOrder() {
        //given for exception
        long empty = 0L;
        when(productServiceMock.findProductById(empty)).thenReturn(Optional.empty());
        when(productServiceMock.findProductById(empty))
                .thenThrow(new NoElementFoundException("product with %d id doesn't exist".formatted(empty)));
        //given
        when(productServiceMock.findProductById(1L)).thenReturn(Optional.of(product));
        when(addressRepositoryMock.save(address)).thenReturn(address);
        when(customerRepositoryMock.save(customer)).thenReturn(customer);
        when(orderRepositoryMock.save(order)).thenReturn(order);
        //when
        orderServiceMock.addNewOrder(orderRequestDto);
        //then and when
        Assertions.assertThatThrownBy(() -> productServiceMock.findProductById(empty))
                .isInstanceOf(NoElementFoundException.class)
                .hasMessage("product with %d id doesn't exist".formatted(empty));
        //then
        Mockito.verify(addressRepositoryMock).save(address);
        Mockito.verify(customerRepositoryMock).save(customer);
        Mockito.verify(orderRepositoryMock).save(order);
    }

    @Test
    @DisplayName("Should get all products with Pageable class")
    public void getAllOrders() {
        //given
        Page<OrderApp> orderApps = mock(Page.class);
        when(orderRepositoryMock.findAll(Mockito.any(Pageable.class))).thenReturn(orderApps);
        //when

        List<OrderResponseDto> allOrders = orderService.getAllOrders(10);
        //then
        Assertions.assertThat(allOrders).isNotNull();
    }

    @Test
    @DisplayName("Should find order by id")
    public void findById() {
        //given
        long id = 1L;
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(order));
        //when
        Optional<OrderResponseDto> byId = orderService.findById(id);
        //then
        Assertions.assertThat(byId).isPresent();
        Assertions.assertThat(byId).isNotNull();
    }

    @Test
    @DisplayName("Should complete order")
    public void completeOrder() {
        //given
        //when
        //then
    }

}


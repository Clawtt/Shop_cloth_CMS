package com.shop_Karol_Herzog_Banasik.order.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.order.customer.Address;
import com.shop_Karol_Herzog_Banasik.order.customer.Customer;
import com.shop_Karol_Herzog_Banasik.order.OrderApp;
import com.shop_Karol_Herzog_Banasik.order.customer.dto.AddressDto;
import com.shop_Karol_Herzog_Banasik.order.customer.dto.CustomerRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderRequestDto;
import com.shop_Karol_Herzog_Banasik.order.dto.OrderResponseDto;
import com.shop_Karol_Herzog_Banasik.order.repositories.AddressRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.CustomerRepository;
import com.shop_Karol_Herzog_Banasik.order.repositories.OrderRepository;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private AddressRepository addressRepositoryMock;
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private LocalDateTimeProvider localDateTimeProviderMock;


    private OrderRequestDto orderRequestDto;
    private Address address;
    private Product product;
    private Customer customer;
    private OrderApp orderToSave;
    private OrderApp emptyOrder;
    private OrderApp orderIsCompleted;
    private OrderService orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        List<Address> addresses = List.of(
                Address.builder()
                        .id(null)
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
                productRepositoryMock
        );

        ProductType productType = new ProductType(1L, "type");

        AddressDto addressDto = new AddressDto(
                true,
                true,
                "street",
                "12",
                "22-222",
                "city"
        );

        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .firstName("Adam")
                .lastName("Smith")
                .phoneNumber(222333444)
                .email("adam@smith.eu")
                .addresses(List.of(addressDto))
                .build();

        address = Address.builder()
                .id(null)
                .invoiceAddress(true)
                .shippingAddress(true)
                .street("street")
                .streetNo("12")
                .zipCode("22-222")
                .city("city")
                .build();

        customer = Customer.builder()
                .id(null)
                .firstName("Adam")
                .lastName("Smith")
                .phoneNumber(222333444)
                .email("adam@smith.eu")
                .addresses(addresses)
                .build();

        product = Product.builder()
                .id(1L)
                .name("T-shirt")
                .price(BigDecimal.valueOf(2000))
                .discountPrice(BigDecimal.valueOf(1500))
                .types(List.of(productType))
                .build();

        orderRequestDto = OrderRequestDto.builder()
                .customer(customerRequestDto)
                .productIds(List.of(product.getId()))
                .build();

        emptyOrder = OrderApp.builder()
                .id(null)
                .build();

        orderToSave = OrderApp.builder()
                .id(null)
                .customer(customer)
                .createdAt(localDateTimeProviderMock.currentTime())
                .isCompleted(false)
                .products(List.of(product))
                .build();

        orderIsCompleted = OrderApp.builder()
                .id(1L)
                .customer(customer)
                .createdAt(localDateTimeProviderMock.currentTime())
                .isCompleted(true)
                .completedAt(localDateTimeProviderMock.currentTime())
                .products(List.of(product))
                .build();
    }

    @Nested
    @DisplayName("Should add new order or throw an exception")
    public class shouldAddNewOrder_Method {
        @Test
        @DisplayName("Should add new order")
        public void addNewOrder() {
            //given
            when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
            when(addressRepositoryMock.save(address)).thenReturn(address);
            when(customerRepositoryMock.save(customer)).thenReturn(customer);
            when(orderRepositoryMock.save(orderToSave)).thenReturn(orderToSave);
            //when
            orderService.addNewOrder(orderRequestDto);
            //then
            Mockito.verify(addressRepositoryMock).save(address);
            Mockito.verify(customerRepositoryMock).save(customer);
            Mockito.verify(orderRepositoryMock).save(orderToSave);
        }

        @Test
        @DisplayName("Should throw NoElementFoundException if product doesn't exist")
        public void shouldThrowNoElementFoundException() {
            //when and then
            Assertions.assertThatThrownBy(
                    () -> orderService.addNewOrder(orderRequestDto))
                    .isInstanceOf(NoElementFoundException.class)
                    .hasMessage("product with %d id doesn't exist".formatted(product.getId()));
        }
    }


    @Test
    @DisplayName("Should get all products with Pageable class")
    public void getAllOrders() {
        //given
        Page<OrderApp> orderApps = mock(Page.class);
        when(orderRepositoryMock.findAll(any(Pageable.class))).thenReturn(orderApps);
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
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderToSave));
        //when
        Optional<OrderResponseDto> findOrderById = orderService.findById(id);
        //then
        assertAll(
                () -> Assertions.assertThat(findOrderById).isPresent(),
                () -> Assertions.assertThat(findOrderById).isNotNull()
        );
    }

    @Nested
    @DisplayName("Should complete order or throw an exception")
    public class shouldCompleteOrder {
        @Test
        @DisplayName("Should set order as a completed")
        public void completeOrder() {
            //given
            when(orderRepositoryMock.findById(orderIsCompleted.getId())).thenReturn(Optional.of(orderIsCompleted));
            when(orderRepositoryMock.save(any(OrderApp.class))).thenReturn(orderIsCompleted);
            //when
            orderService.completeOrder(orderIsCompleted.getId(), true);
            //then
            Mockito.verify(orderRepositoryMock).save(orderIsCompleted);
        }
        @Test
        @DisplayName("Should throw NoElementFoundException if order doesn't exist")
        public void shouldThrowNoElementFoundException() {
            //when and then
            Assertions.assertThatThrownBy(
                            () -> orderService.completeOrder(emptyOrder.getId(), true))
                    .isInstanceOf(NoElementFoundException.class)
                    .hasMessage("order with %d id doesn't exist".formatted(emptyOrder.getId()));
        }
    }
}


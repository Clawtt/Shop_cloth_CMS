package com.shop_Karol_Herzog_Banasik.exceptions;

import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class NoElementFoundExceptionTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldThrowNoElementFoundException() {
        //given
        //when
        when(productRepository.findById(0L))
                .thenThrow(new NoElementFoundException("Not found"));

        //then
        Assertions.assertThatThrownBy(() -> productRepository.findById(0L))
                .isInstanceOf(NoElementFoundException.class)
                .hasMessage("Not found");
    }
}

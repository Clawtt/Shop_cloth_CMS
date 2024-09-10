package com.shop_Karol_Herzog_Banasik.globalAdviceController;

import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;
import com.shop_Karol_Herzog_Banasik.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
   public void handleNoElementFoundException_ShouldThrownAnException_WhenObjectDoesNotExistsInDatabase() throws Exception {

        //given
        Long id = 1L;
        when(productService.getProductById(id)).thenReturn(Optional.empty());

        //when and then
        mockMvc.perform(get("/product/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Product with %d id not found in database".formatted(id)));
    }
}

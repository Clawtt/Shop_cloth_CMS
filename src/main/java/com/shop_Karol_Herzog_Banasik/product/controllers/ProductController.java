package com.shop_Karol_Herzog_Banasik.product.controllers;

import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;


    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "10") int page) {
        List<ProductDto> allProducts = productService.getAllProducts(page);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable Long id
    ) {
        ProductDto productDto = productService.getProductById(id).orElseThrow(
                () -> new NoElementFoundException("Product with %d id not found in database".formatted(id)));
        if (productService.getProductById(id).isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productDto);
    }


    @PostMapping(value = "/product/add")
    public ResponseEntity<ProductDto> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        productService.saveNewProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(@Valid
                                                    @RequestBody ProductDto productDto,
                                                    @PathVariable Long id) {
        if (productService.getProductById(id).isPresent()) {
            productService.updateProduct(id, productDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        productService.getProductById(id).orElseThrow(
                () -> new NoElementFoundException("product with %d id doesn't exist".formatted(id)
        ));
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


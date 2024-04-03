package com.example.shop_karolherzogbanasik.product.controllers;

import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.dto.ProductDto;
import com.example.shop_karolherzogbanasik.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;


    /**
     *
     * @param page set number of pages
     *             -- default 10
     *             -- not required
     * @return list of products
     * @route ../products
     *
     */
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "10") int page) {
        List<ProductDto> allProducts = productService.getAllProducts(page);
        return ResponseEntity.ok(allProducts);
    }

    /**
     *
     * @param id product id
     * @return product
     * @throws NoElementFoundException
     * @route ../product/id
     */

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


    /**
     * @return new product into database
     * @route ../product/add
     */
    @PostMapping(value = "/product/add")
    public ResponseEntity<ProductDto> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        productService.saveNewProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param id product id
     * @return updating product in database by id
     * @route ../product/id
     */
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
                                                    @PathVariable Long id) {
        if (productService.getProductById(id).isPresent()) {
            productService.updateProduct(productDto, id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.notFound().build();
    }


    /**
     *
     * @param id product id
     * @return deleting product from database by id
     * @route ../product/delete/id
     */
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        if (!productService.getProductById(id).isEmpty()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidEx(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoElementFoundException.class)
    Map<String, String> handleNoElementFoundException(NoElementFoundException exception) {
        HashMap<String, String> exMap = new HashMap<>();
        exMap.put("message", exception.getMessage());
        return exMap;
    }
}


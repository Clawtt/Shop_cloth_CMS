package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;
import com.shop_Karol_Herzog_Banasik.product.dto.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     *
     * @return list of products
     */
    @Override
    public List<ProductDto> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>(productRepository.findAll());
        return products.stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());

    }

    /**
     *
     * @param pageSize the number of the products to be returned
     * @return list of products
     */

    @Override
    public List<ProductDto> getAllProducts(int pageSize) {
        Pageable page = Pageable.ofSize(pageSize);
        return productRepository
                .findAll(page)
                .stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param productDto product to save in database
     */
    @Override
    public void saveNewProduct(ProductDto productDto) {
        List<ProductType> types = productDto.getTypes()
                .stream()
                .map(t -> new ProductType(t.getId(), t.getName()))
                .collect(Collectors.toList());
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setTypes(types);
        productRepository.save(product);
    }

    /**
     *
     * @param id of product from database
     * @return product from database
     */
    @Override
    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::map);
    }

    /**
     *
     * @param id to find product to updating
     * @param productDto new product
     */
    @Transactional
    @Override
    public void updateProduct(Long id, ProductDto productDto) {
        List<ProductType> types = new ArrayList<>();
        ArrayList<ProductTypeDto> dtoTypes = new ArrayList<>(productDto.getTypes());
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NoElementFoundException("product at %d id has no exist".formatted(id)));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());

        for (ProductTypeDto type : dtoTypes) {
            ProductType productType = new ProductType();
            productType.setName(type.getName());
            types.add(productType);
        }
        product.setTypes(types);
    }

    /**
     *
     * @param id of product to remove from database
     */
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NoElementFoundException("product at %d id has no exist".formatted(id)));

        productRepository.delete(product);
    }
}


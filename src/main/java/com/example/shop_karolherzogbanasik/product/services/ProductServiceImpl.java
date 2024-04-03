package com.example.shop_karolherzogbanasik.product.services;

import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.ProductRepository;
import com.example.shop_karolherzogbanasik.product.ProductType;
import com.example.shop_karolherzogbanasik.product.dto.ProductDto;
import com.example.shop_karolherzogbanasik.product.dto.mapper.ProductMapper;
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

    @Override
    public List<ProductDto> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>(productRepository.findAll());
        return products.stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> getAllProducts(int pageSize) {
        Pageable page = Pageable.ofSize(pageSize);
        return productRepository
                .findAll(page)
                .stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void saveNewProduct(ProductDto productDto) {
        ArrayList<ProductType> types = new ArrayList<>();
        productDto.getTypes()
                .stream()
                .map(type -> types)
                .collect(Collectors.toList());
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setTypes(types);
        productRepository.save(product);
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::map);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateProduct(ProductDto productDto, Long id) {
        List<ProductType> types = new ArrayList<>();
        ArrayList<String> dtoTypes = new ArrayList<>(productDto.getTypes());
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NoElementFoundException("product at %d id has no exist".formatted(id)));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());
        for (String type : dtoTypes) {
            ProductType productType = new ProductType();
            productType.setName(type);
            types.add(productType);
        }
        product.setTypes(types);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NoElementFoundException("product at %d id has no exist".formatted(id)));
        productRepository.delete(product);
    }
}


package com.ipodolak.adventureworks.service.production;

import com.ipodolak.adventureworks.dto.production.ProductDto;
import com.ipodolak.adventureworks.entity.production.Product;
import com.ipodolak.adventureworks.mapper.production.ProductMapper;
import com.ipodolak.adventureworks.repository.production.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<ProductDto> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findBySubcategory(Integer subcategoryId) {
        return productRepository.findByProductSubcategoryId(subcategoryId).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findByModel(Integer modelId) {
        return productRepository.findByProductModelId(modelId).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findByColor(String color) {
        return productRepository.findByColor(color).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findActiveProducts() {
        return productRepository.findActiveProducts().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<String> findAllColors() {
        return productRepository.findAllColors();
    }

    public List<ProductDto> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto create(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto update(Integer id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Product updatedProduct = productMapper.toEntity(productDto);
        updatedProduct.setProductId(existingProduct.getProductId());
        updatedProduct.setRowguid(existingProduct.getRowguid());

        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}

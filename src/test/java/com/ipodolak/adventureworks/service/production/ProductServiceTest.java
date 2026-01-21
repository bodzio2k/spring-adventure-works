package com.ipodolak.adventureworks.service.production;

import com.ipodolak.adventureworks.entity.production.Product;
import com.ipodolak.adventureworks.repository.production.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductDto testProductDto;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId(1);
        testProduct.setName("Test Bike");
        testProduct.setProductNumber("TB-001");
        testProduct.setMakeFlag(true);
        testProduct.setFinishedGoodsFlag(true);
        testProduct.setColor("Red");
        testProduct.setSafetyStockLevel((short) 100);
        testProduct.setReorderPoint((short) 75);
        testProduct.setStandardCost(new BigDecimal("500.00"));
        testProduct.setListPrice(new BigDecimal("1000.00"));
        testProduct.setDaysToManufacture(4);
        testProduct.setSellStartDate(LocalDateTime.now());
        testProduct.setRowguid(UUID.randomUUID());
        testProduct.setModifiedDate(LocalDateTime.now());

        testProductDto = new ProductDto();
        testProductDto.setProductId(1);
        testProductDto.setName("Test Bike");
        testProductDto.setProductNumber("TB-001");
        testProductDto.setListPrice(new BigDecimal("1000.00"));
    }

    @Test
    void shouldFindAllProducts() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Bike");
        verify(productRepository).findAll();
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void shouldFindProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Test Bike");
        verify(productRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found with id: 999");
    }

    @Test
    void shouldFindProductsByName() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.findByNameContainingIgnoreCase("Bike")).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findByName("Bike");

        assertThat(result).hasSize(1);
        verify(productRepository).findByNameContainingIgnoreCase("Bike");
    }

    @Test
    void shouldFindProductsBySubcategory() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.findByProductSubcategoryId(1)).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findBySubcategory(1);

        assertThat(result).hasSize(1);
        verify(productRepository).findByProductSubcategoryId(1);
    }

    @Test
    void shouldFindProductsByColor() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.findByColor("Red")).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findByColor("Red");

        assertThat(result).hasSize(1);
        verify(productRepository).findByColor("Red");
    }

    @Test
    void shouldFindActiveProducts() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.findActiveProducts()).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findActiveProducts();

        assertThat(result).hasSize(1);
        verify(productRepository).findActiveProducts();
    }

    @Test
    void shouldFindAllColors() {
        List<String> colors = Arrays.asList("Red", "Blue", "Black");
        when(productRepository.findAllColors()).thenReturn(colors);

        List<String> result = productService.findAllColors();

        assertThat(result).hasSize(3);
        assertThat(result).contains("Red", "Blue", "Black");
        verify(productRepository).findAllColors();
    }

    @Test
    void shouldFindProductsByPriceRange() {
        BigDecimal minPrice = new BigDecimal("500.00");
        BigDecimal maxPrice = new BigDecimal("1500.00");
        List<Product> products = Collections.singletonList(testProduct);

        when(productRepository.findByPriceRange(minPrice, maxPrice)).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        List<ProductDto> result = productService.findByPriceRange(minPrice, maxPrice);

        assertThat(result).hasSize(1);
        verify(productRepository).findByPriceRange(minPrice, maxPrice);
    }

    @Test
    void shouldCreateProduct() {
        when(productMapper.toEntity(testProductDto)).thenReturn(testProduct);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.create(testProductDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Bike");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productMapper.toEntity(testProductDto)).thenReturn(testProduct);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.update(1, testProductDto);

        assertThat(result).isNotNull();
        verify(productRepository).findById(1);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(999, testProductDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found with id: 999");
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.existsById(1)).thenReturn(true);

        productService.delete(1);

        verify(productRepository).existsById(1);
        verify(productRepository).deleteById(1);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(false);

        assertThatThrownBy(() -> productService.delete(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found with id: 999");

        verify(productRepository, never()).deleteById(anyInt());
    }
}

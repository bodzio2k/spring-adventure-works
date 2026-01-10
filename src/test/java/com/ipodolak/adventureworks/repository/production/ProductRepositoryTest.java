package com.ipodolak.adventureworks.repository.production;

import com.ipodolak.adventureworks.entity.production.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindAllProducts() {
        List<Product> products = productRepository.findAll();
        assertThat(products).isNotNull();
    }

    @Test
    void shouldFindProductById() {
        Optional<Product> product = productRepository.findById(1);
        assertThat(product).isPresent();
    }

    @Test
    void shouldFindProductsByNameContainingIgnoreCase() {
        List<Product> products = productRepository.findByNameContainingIgnoreCase("bike");
        assertThat(products).isNotEmpty();
        assertThat(products).allMatch(p ->
            p.getName().toLowerCase().contains("bike"));
    }

    @Test
    void shouldFindProductsBySubcategory() {
        List<Product> products = productRepository.findByProductSubcategoryId(1);
        assertThat(products).isNotNull();
    }

    @Test
    void shouldFindProductsByModel() {
        List<Product> products = productRepository.findByProductModelId(1);
        assertThat(products).isNotNull();
    }

    @Test
    void shouldFindProductsByColor() {
        List<Product> products = productRepository.findByColor("Black");
        assertThat(products).isNotNull();
        if (!products.isEmpty()) {
            assertThat(products).allMatch(p -> "Black".equals(p.getColor()));
        }
    }

    @Test
    void shouldFindActiveProducts() {
        List<Product> activeProducts = productRepository.findActiveProducts();
        assertThat(activeProducts).isNotNull();
        assertThat(activeProducts).allMatch(p -> p.getDiscontinuedDate() == null);
    }

    @Test
    void shouldFindAllColors() {
        List<String> colors = productRepository.findAllColors();
        assertThat(colors).isNotNull();
        assertThat(colors).doesNotContainNull();
    }

    @Test
    void shouldFindProductsByPriceRange() {
        BigDecimal minPrice = new BigDecimal("100.00");
        BigDecimal maxPrice = new BigDecimal("500.00");

        List<Product> products = productRepository.findByPriceRange(minPrice, maxPrice);

        assertThat(products).isNotNull();
        if (!products.isEmpty()) {
            assertThat(products).allMatch(p ->
                p.getListPrice().compareTo(minPrice) >= 0 &&
                p.getListPrice().compareTo(maxPrice) <= 0
            );
        }
    }

    @Test
    void shouldReturnEmptyListWhenSearchingNonExistentName() {
        List<Product> products = productRepository.findByNameContainingIgnoreCase("NonExistentProductName12345");
        assertThat(products).isEmpty();
    }
}

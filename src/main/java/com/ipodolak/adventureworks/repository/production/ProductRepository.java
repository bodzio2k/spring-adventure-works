package com.ipodolak.adventureworks.repository.production;

import com.ipodolak.adventureworks.entity.production.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByProductSubcategoryId(Integer subcategoryId);

    List<Product> findByProductModelId(Integer modelId);

    List<Product> findByColor(String color);

    @Query("SELECT p FROM Product p WHERE p.discontinuedDate IS NULL")
    List<Product> findActiveProducts();

    @Query("SELECT DISTINCT p.color FROM Product p WHERE p.color IS NOT NULL ORDER BY p.color")
    List<String> findAllColors();

    @Query("SELECT p FROM Product p WHERE p.listPrice BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") java.math.BigDecimal minPrice,
                                     @Param("maxPrice") java.math.BigDecimal maxPrice);
}

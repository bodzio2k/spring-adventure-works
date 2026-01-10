package com.ipodolak.adventureworks.controller.production;

import com.ipodolak.adventureworks.dto.production.ProductDto;
import com.ipodolak.adventureworks.service.production.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<List<ProductDto>> getBySubcategory(@PathVariable Integer subcategoryId) {
        return ResponseEntity.ok(productService.findBySubcategory(subcategoryId));
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<List<ProductDto>> getByModel(@PathVariable Integer modelId) {
        return ResponseEntity.ok(productService.findByModel(modelId));
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<ProductDto>> getByColor(@PathVariable String color) {
        return ResponseEntity.ok(productService.findByColor(color));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        return ResponseEntity.ok(productService.findActiveProducts());
    }

    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAllColors() {
        return ResponseEntity.ok(productService.findAllColors());
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> getByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.findByPriceRange(minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

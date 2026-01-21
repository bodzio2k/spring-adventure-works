package com.ipodolak.adventureworks.controller.production;

import com.adventureworks.model.Product;
import com.ipodolak.adventureworks.mapper.ProductMapper;
import com.ipodolak.adventureworks.service.production.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productMapper.toDtoList(productService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productMapper.toDto(productService.get(id)));
    }
}

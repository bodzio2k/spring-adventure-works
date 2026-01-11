package com.ipodolak.adventureworks.controller.production;

import com.ipodolak.adventureworks.dto.production.ProductDto;
import com.ipodolak.adventureworks.service.production.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductModelAssembler productModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getAllProducts() {
        List<EntityModel<ProductDto>> products = productService.findAll().stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel(),
                linkTo(methodOn(ProductController.class).getActiveProducts()).withRel("active-products"),
                linkTo(methodOn(ProductController.class).getAllColors()).withRel("colors"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDto>> getProductById(@PathVariable Integer id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(productModelAssembler.toModel(product));
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> searchByName(@RequestParam String name) {
        List<EntityModel<ProductDto>> products = productService.findByName(name).stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).searchByName(name)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getBySubcategory(@PathVariable Integer subcategoryId) {
        List<EntityModel<ProductDto>> products = productService.findBySubcategory(subcategoryId).stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getBySubcategory(subcategoryId)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getByModel(@PathVariable Integer modelId) {
        List<EntityModel<ProductDto>> products = productService.findByModel(modelId).stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getByModel(modelId)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getByColor(@PathVariable String color) {
        List<EntityModel<ProductDto>> products = productService.findByColor(color).stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getByColor(color)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"),
                linkTo(methodOn(ProductController.class).getAllColors()).withRel("colors"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/active")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getActiveProducts() {
        List<EntityModel<ProductDto>> products = productService.findActiveProducts().stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getActiveProducts()).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAllColors() {
        return ResponseEntity.ok(productService.findAllColors());
    }

    @GetMapping("/price-range")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<EntityModel<ProductDto>> products = productService.findByPriceRange(minPrice, maxPrice).stream()
                .map(productModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<ProductDto>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getByPriceRange(minPrice, maxPrice)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductDto>> createProduct(@RequestBody ProductDto productDto) {
        ProductDto created = productService.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productModelAssembler.toModel(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDto>> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductDto productDto) {
        ProductDto updated = productService.update(id, productDto);
        return ResponseEntity.ok(productModelAssembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

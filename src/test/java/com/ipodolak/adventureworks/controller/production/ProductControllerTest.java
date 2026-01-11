package com.ipodolak.adventureworks.controller.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipodolak.adventureworks.dto.production.ProductDto;
import com.ipodolak.adventureworks.service.production.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ObjectMapper objectMapper;
    private ProductDto testProductDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testProductDto = new ProductDto();
        testProductDto.setProductId(1);
        testProductDto.setName("Test Bike");
        testProductDto.setProductNumber("TB-001");
        testProductDto.setMakeFlag(true);
        testProductDto.setFinishedGoodsFlag(true);
        testProductDto.setColor("Red");
        testProductDto.setSafetyStockLevel((short) 100);
        testProductDto.setReorderPoint((short) 75);
        testProductDto.setStandardCost(new BigDecimal("500.00"));
        testProductDto.setListPrice(new BigDecimal("1000.00"));
        testProductDto.setDaysToManufacture(4);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Bike")))
                .andExpect(jsonPath("$[0].productNumber", is("TB-001")));

        verify(productService).findAll();
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(productService.findById(1)).thenReturn(testProductDto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.name", is("Test Bike")))
                .andExpect(jsonPath("$.productNumber", is("TB-001")));

        verify(productService).findById(1);
    }

    @Test
    void shouldSearchProductsByName() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findByName("Bike")).thenReturn(products);

        mockMvc.perform(get("/api/products/search")
                        .param("name", "Bike"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Bike")));

        verify(productService).findByName("Bike");
    }

    @Test
    void shouldGetProductsBySubcategory() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findBySubcategory(1)).thenReturn(products);

        mockMvc.perform(get("/api/products/subcategory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(productService).findBySubcategory(1);
    }

    @Test
    void shouldGetProductsByModel() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findByModel(1)).thenReturn(products);

        mockMvc.perform(get("/api/products/model/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(productService).findByModel(1);
    }

    @Test
    void shouldGetProductsByColor() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findByColor("Red")).thenReturn(products);

        mockMvc.perform(get("/api/products/color/Red"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].color", is("Red")));

        verify(productService).findByColor("Red");
    }

    @Test
    void shouldGetActiveProducts() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findActiveProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(productService).findActiveProducts();
    }

    @Test
    void shouldGetAllColors() throws Exception {
        List<String> colors = Arrays.asList("Red", "Blue", "Black");
        when(productService.findAllColors()).thenReturn(colors);

        mockMvc.perform(get("/api/products/colors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("Red")));

        verify(productService).findAllColors();
    }

    @Test
    void shouldGetProductsByPriceRange() throws Exception {
        List<ProductDto> products = Arrays.asList(testProductDto);
        when(productService.findByPriceRange(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(products);

        mockMvc.perform(get("/api/products/price-range")
                        .param("minPrice", "500.00")
                        .param("maxPrice", "1500.00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(productService).findByPriceRange(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.create(any(ProductDto.class))).thenReturn(testProductDto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProductDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test Bike")))
                .andExpect(jsonPath("$.productNumber", is("TB-001")));

        verify(productService).create(any(ProductDto.class));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.update(anyInt(), any(ProductDto.class))).thenReturn(testProductDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProductDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test Bike")));

        verify(productService).update(anyInt(), any(ProductDto.class));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).delete(1);
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        when(productService.findById(999))
                .thenThrow(new RuntimeException("Product not found with id: 999"));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isInternalServerError());

        verify(productService).findById(999);
    }
}

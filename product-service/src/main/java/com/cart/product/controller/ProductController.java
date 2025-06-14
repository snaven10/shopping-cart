package com.cart.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cart.product.dto.ProductDto;
import com.cart.product.service.ProductService;
import com.cart.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

     private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("Products fetched", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Product fetched", product));
    }

    @GetMapping("/internal/{id}")
    public ProductDto getByIdInternal(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }
}

package com.example.first.controllers;

import com.example.first.dtos.ProductRecordDto;
import com.example.first.models.ProductModel;
import com.example.first.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {
     @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(productModel.get());
    }
    
}

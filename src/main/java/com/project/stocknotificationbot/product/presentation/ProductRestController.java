package com.project.stocknotificationbot.product.presentation;

import com.project.stocknotificationbot.product.application.service.ProductService;
import com.project.stocknotificationbot.product.presentation.dto.request.CreateProductRequest;
import com.project.stocknotificationbot.product.presentation.dto.response.CreateProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(
            @RequestBody
            @Valid
            CreateProductRequest request,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();

        }

        CreateProductResponse createProductResponse = productService.create(request);

        return ResponseEntity.ok(createProductResponse);
    }
}

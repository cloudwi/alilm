package com.project.stocknotificationbot.product.application.service;

import com.project.stocknotificationbot.product.presentation.dto.request.CreateProductRequest;
import com.project.stocknotificationbot.product.presentation.dto.response.CreateProductResponse;

public interface ProductService {

    CreateProductResponse create(CreateProductRequest request);
}
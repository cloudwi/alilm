package com.project.stocknotificationbot.product.presentation.dto.response;

import com.project.stocknotificationbot.product.domain.entity.Product;

public record CreateProductResponse(
        Long id,
        Long realId,
        String size,
        String title,
        String imageUrl
) {

    public static CreateProductResponse from(Product savedProduct) {
        return new CreateProductResponse(
                savedProduct.getId(),
                savedProduct.getRealId(),
                savedProduct.getSize(),
                savedProduct.getTitle(),
                savedProduct.getImageUrl()
        );
    }
}

package com.project.stocknotificationbot.product.presentation.dto.request;

import com.project.stocknotificationbot.product.domain.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateProductRequest(
        @NotBlank
        @Pattern(regexp = "^[0-9]*$")
        Long realId,
        @NotBlank
        @Pattern(regexp = "S^[0-9a-zA-Z]*$")
        String size
) {

    public Product toProduct(String title, String imageUrl) {
        return new Product(
                this.realId,
                this.size,
                title,
                imageUrl
        );
    }
}

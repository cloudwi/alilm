package com.project.musinsastocknotificationbot.domain.product.controller;

import com.project.musinsastocknotificationbot.domain.product.dto.Response.ProductFindAllResponseDto;
import com.project.musinsastocknotificationbot.domain.product.dto.Response.ProductSaveResponseDto;
import com.project.musinsastocknotificationbot.domain.product.dto.request.ProductSaveRequestDto;
import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.domain.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductSaveResponseDto> save(@RequestBody @Valid ProductSaveRequestDto productSaveRequestDto) {
        ProductId productId = productService.save(productSaveRequestDto.getId(), productSaveRequestDto.getSize());
        ProductSaveResponseDto productSaveResponseDto = new ProductSaveResponseDto(productId.getId(), productId.getSize());
        return ResponseEntity.ok(productSaveResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<ProductFindAllResponseDto>> findAll() {
        List<ProductFindAllResponseDto> productFindAllResponseDtos = new ArrayList<>();
        List<Product> products = productService.findAll();

        products.forEach(product -> {
            productFindAllResponseDtos.add(new ProductFindAllResponseDto(
                    product.getProductId().getId(),
                    product.getProductId().getSize()));
        });
        return ResponseEntity.ok(productFindAllResponseDtos);
    }

    @DeleteMapping("/{id}/{size}")
    public ResponseEntity<Long> delete(
            @PathVariable("id") @NotBlank(message = "삭제 시 id는 필수 입니다.") long id,
            @PathVariable("size") @NotBlank(message = "삭제 시 size는 필수 입니다.") String size) {
        ProductId productId = new ProductId(id, size);
        return ResponseEntity.ok(productService.delete(productId));
    }
}

package com.project.musinsastocknotificationbot.product.controller;

import com.project.musinsastocknotificationbot.product.domain.dto.Response.ProductFindAllResponseDto;
import com.project.musinsastocknotificationbot.product.domain.dto.Response.ProductSaveResponseDto;
import com.project.musinsastocknotificationbot.product.domain.dto.request.ProductSaveRequestDto;
import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.idClass.ProductId;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductSaveResponseDto> save(
        @RequestBody
        @Validated
        ProductSaveRequestDto productSaveRequestDto
    ) {
        ProductId productId = productService.save(productSaveRequestDto.id(),
            productSaveRequestDto.size());
        ProductSaveResponseDto productSaveResponseDto = new ProductSaveResponseDto(
            productId.getId(), productId.getSize());
        return ResponseEntity.ok(productSaveResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<ProductFindAllResponseDto>> findAll() {
        List<ProductFindAllResponseDto> productFindAllResponseDtos = new ArrayList<>();
        List<Product> products = productService.findAll();

        products.forEach(
            product -> productFindAllResponseDtos.add(ProductFindAllResponseDto.from(product)));

        return ResponseEntity.ok(productFindAllResponseDtos);
    }

    @DeleteMapping("/{id}/{size}")
    public ResponseEntity<Long> delete(
            @PathVariable("id")
            @NotBlank(message = "삭제 시 id는 필수 입니다.")
            long id,

            @PathVariable("size")
            @NotBlank(message = "삭제 시 size는 필수 입니다.")
            @Pattern(regexp = "[A-Z0-9]+", message = "사이즈 형식에 맞지 않습니다.")
            String size
    ) {
        ProductId productId = new ProductId(id, size);
        return ResponseEntity.ok(productService.delete(productId));
    }
}

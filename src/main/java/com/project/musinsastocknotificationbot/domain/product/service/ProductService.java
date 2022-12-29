package com.project.musinsastocknotificationbot.domain.product.service;

import com.project.musinsastocknotificationbot.domain.product.dto.request.ProductSaveRequestDto;
import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductId save(ProductSaveRequestDto productSaveRequestDto) {
        ProductId productId = new ProductId(productSaveRequestDto.getId(), productSaveRequestDto.getSize());
        Product product = new Product(productId);
        return productRepository.save(product).getProductId();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public long delete(long id, String size) {
        ProductId productId = new ProductId(id, size);
        productRepository.deleteById(productId);
        return id;
    }

}

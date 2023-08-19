package com.project.stocknotificationbot.product.domain.repository;

import com.project.stocknotificationbot.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

package com.project.musinsastocknotificationbot.product.domain.repository;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.idClass.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}

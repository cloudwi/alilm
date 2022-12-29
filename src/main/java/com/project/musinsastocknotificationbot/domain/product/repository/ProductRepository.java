package com.project.musinsastocknotificationbot.domain.product.repository;

import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}

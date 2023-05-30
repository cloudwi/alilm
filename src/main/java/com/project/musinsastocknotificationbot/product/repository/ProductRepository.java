package com.project.musinsastocknotificationbot.product.repository;

import com.project.musinsastocknotificationbot.product.entity.Product;
import com.project.musinsastocknotificationbot.product.entity.idClass.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}

package com.project.musinsastocknotificationbot.domain.product.service;

import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    ProductId save(long id, String size);

    List<Product> findAll();

    long delete(ProductId productId);
}

package com.project.musinsastocknotificationbot.product.service;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.idClass.ProductId;

import java.util.List;

public interface ProductService {

    ProductId save(long id, String size);

    List<Product> findAll();

    long delete(ProductId productId);
}

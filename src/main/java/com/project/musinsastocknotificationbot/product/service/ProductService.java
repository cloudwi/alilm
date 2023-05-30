package com.project.musinsastocknotificationbot.product.service;

import com.project.musinsastocknotificationbot.product.entity.Product;
import com.project.musinsastocknotificationbot.product.entity.idClass.ProductId;

import java.util.List;

public interface ProductService {

    ProductId save(long id, String size);

    List<Product> findAll();

    long delete(ProductId productId);
}

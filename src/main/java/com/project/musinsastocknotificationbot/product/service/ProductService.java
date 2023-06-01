package com.project.musinsastocknotificationbot.product.service;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;

import java.util.List;

public interface ProductService {

    ProductInfo save(long id, String size);

    List<Product> findAll();

    long delete(ProductInfo productInfo);
}

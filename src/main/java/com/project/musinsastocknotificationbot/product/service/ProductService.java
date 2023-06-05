package com.project.musinsastocknotificationbot.product.service;

import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;

public interface ProductService {

    void save(ProductInfo productInfo);

    void findAll();

    void delete(ProductInfo productInfo);
}

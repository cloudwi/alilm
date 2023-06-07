package com.project.musinsastocknotificationbot.domain.product.service;

import com.project.musinsastocknotificationbot.domain.product.entity.vo.ProductInfo;

public interface ProductService {

    void save(ProductInfo productInfo);

    void findAll();

    void delete(ProductInfo productInfo);
}

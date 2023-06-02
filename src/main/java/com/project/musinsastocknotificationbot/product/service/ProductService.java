package com.project.musinsastocknotificationbot.product.service;

public interface ProductService {

    void save(String[] inputMessage);

    void findAll();

    void delete(String[] inputMessage);
}

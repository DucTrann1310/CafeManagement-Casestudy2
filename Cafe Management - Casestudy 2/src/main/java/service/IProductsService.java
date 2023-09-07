package service;

import models.*;

import java.util.List;

public interface IProductsService {
    List<Products> getAll();
    void updateProduct(int id, Products product);
//    void deleteProduct(int id);
    void createProduct(Products product);
    Products findProductById(int id);
    Products findProductByName(String str);
    List<Products> findProductByPrice(int price);
    List<Products> findProductByCategory(ECategory eCategory);
}

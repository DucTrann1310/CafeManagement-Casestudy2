package service;

import models.ECategory;
import models.Products;
import utils.FileUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsService implements IProductsService {
    private final String fileProduct = "./Data/products.txt";
    @Override
    public List<Products> getAll() {
        return FileUtils.readData(fileProduct, Products.class);
    }

    @Override
    public void updateProduct(int id, Products product) {
        List<Products> products = getAll();
        for (Products p : products) {
            if (p.getId() == id) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setCategory(product.getCategory());
                break;
            }
        }
        FileUtils.writeData(fileProduct,products);
    }

//    @Override
//    public void deleteProduct(int id) {
//        List<Products> products = getAll();
//        products.removeIf(p -> p.getId() == id);
//        products.subList(id-1, products.size()).stream().forEach(product -> product.setId(product.getId()-1));
//        FileUtils.writeData(fileProduct,products);
//    }

    @Override
    public void createProduct(Products product) {
        List<Products> products = getAll();
        products.add(product);

        FileUtils.writeData(fileProduct,products);
    }

    @Override
    public Products findProductById(int id) {
        List<Products> products = getAll();
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

    }

    @Override
    public Products findProductByName(String str) {
        List<Products> products = getAll();
        return products.stream().filter(p -> p.getName().equals(str)).findFirst().orElse(null);
    }

    @Override
    public List<Products> findProductByPrice(int price) {
        List<Products> products = getAll();
        return products.stream().filter(p -> p.getPrice() == price).collect(Collectors.toList());
    }


    @Override
    public List<Products> findProductByCategory(ECategory eCategory) {
        List<Products> products = getAll();
        return products.stream().filter(p -> p.getCategory() == eCategory).collect(Collectors.toList());
    }

}

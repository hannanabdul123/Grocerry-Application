package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Product;

public interface ProductService {
    Product addProduct(Product product);
    List<Product>getAllProducts();
    void deleteProduct(Long product_id);
     Product getByIProduct(Long product_id);
}

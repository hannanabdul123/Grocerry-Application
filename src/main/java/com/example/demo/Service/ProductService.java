package com.example.demo.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Product;

public interface ProductService {
    Product addProduct(Product product);
    List<Product>getAllProducts();
    void deleteProduct(Long product_id);
     Product getByProductID(Long product_id);
}

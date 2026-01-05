package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo){
        this.repo=repo;
    }
    public Product addProduct(Product product){
        return repo.save(product);
    }
    public List<Product> getAllProducts(){
        return repo.findAll();
    }
}

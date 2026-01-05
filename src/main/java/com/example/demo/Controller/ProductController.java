package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service){
        this.service=service;
    }

   @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @GetMapping("/allproducts")
    public List<Product> getAll() {
        return service.getAllProducts();
    
}
}

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
    
    @Override
    public Product addProduct(Product product){     
  try {
    System.out.println("Images y hai: "+product.getImageUrl());
    return repo.save(product);
  } catch (Exception e) {
   throw new RuntimeException("Failed to add product: "+e.getMessage());
  }              
    }

     @Override
    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    @Override
    public void deleteProduct(Long product_id){
        Product product=repo.findById(product_id).orElseThrow(()-> new RuntimeException("Product not found!"));

       repo.deleteById(product.getProduct_id());
    }
    @Override
    public Product getByIProduct(Long product_id){
        return repo.findById(product_id).orElseThrow(()-> new RuntimeException("Product not found!"));
       
        
    }
}

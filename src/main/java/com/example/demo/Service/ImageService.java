package com.example.demo.Service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService{

    
    private Cloudinary cloudinary;
    public ImageService(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }
    public String uploadImage(MultipartFile file) throws IOException {

        // ✅ File ko cloudinary pe upload karo
        Map result = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.emptyMap()
        );

        
        return result.get("secure_url").toString();
        
    }
}

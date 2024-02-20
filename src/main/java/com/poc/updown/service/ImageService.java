package com.poc.updown.service;

import com.poc.updown.entity.ImageData;
import com.poc.updown.repository.ImageRepository;
import com.poc.updown.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageUtils imageUtils;

    public ImageService(ImageRepository imageRepository, ImageUtils imageUtils) {
        this.imageRepository = imageRepository;
        this.imageUtils = imageUtils;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = imageRepository.save(new ImageData(file.getOriginalFilename(), file.getContentType(), imageUtils.compressImage(file.getBytes())));

        if (imageData != null) {
            return "file uploaded successfully: " + file.getOriginalFilename();
        }

        return null;
    }

    public byte[] downloadImage(String fileName) {
       Optional<ImageData> dbImage = imageRepository.findByName(fileName);
       byte[] image = imageUtils.decompressImage(dbImage.get().getImageData());
       return image;
    }

}

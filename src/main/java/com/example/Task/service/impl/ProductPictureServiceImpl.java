package com.example.Task.service.impl;

import com.example.Task.dto.ProductPictureDTO;
import com.example.Task.entity.ProductEntity;
import com.example.Task.entity.ProductPictureEntity;
import com.example.Task.repository.ProductPictureRepository;
import com.example.Task.service.ProductPictureService;
import com.example.Task.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPictureServiceImpl implements ProductPictureService {
    private final ProductService productService;
    private final ProductPictureRepository productPictureRepository;

    @Override
    public String saveAccountImage(String idProduct, String pictureLink) {
        ProductEntity productEntity = productService.findProductById(idProduct);
        ProductPictureEntity productPictureEntity = new ProductPictureEntity();
        productPictureEntity.setProduct(productEntity);
        productPictureEntity.setLinkOfImages(pictureLink);
        productPictureRepository.save(productPictureEntity);
        return productEntity.getId();
    }

    @Override
    public List<ProductPictureDTO> findAll(Integer productId) {
        return null;
    }
}

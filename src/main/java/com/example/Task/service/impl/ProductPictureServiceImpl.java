package com.example.Task.service.impl;

import com.example.Task.entity.ProductEntity;
import com.example.Task.entity.ProductPictureEntity;
import com.example.Task.entity.enums.ProductMode;
import com.example.Task.repository.ProductPictureRepository;
import com.example.Task.repository.ProductRepository;
import com.example.Task.service.ProductPictureService;
import com.example.Task.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPictureServiceImpl implements ProductPictureService {
    private final ProductService productService;
    private final ProductPictureRepository productPictureRepository;
    private final ProductRepository productRepository;

    @Override
    public String saveAccountImage(String idProduct, String pictureLink) {
        ProductEntity productEntity = productService.findProductById(idProduct);
        ProductPictureEntity productPictureEntity = new ProductPictureEntity();
        productPictureEntity.setProduct(productEntity);
        productPictureEntity.setLinkOfImages(pictureLink);
        productPictureRepository.save(productPictureEntity);
        savePictureWithManualMode(productEntity);
        return productEntity.getId();
    }

    private void savePictureWithManualMode(ProductEntity productEntity) {
        if(productEntity.getFlag().equals(ProductMode.AUTO_MODE.getStr())) {
            productEntity.setFlag(ProductMode.MANUAL_MODE.getStr());
            productRepository.save(productEntity);
        }
    }

    @Override
    public void deleteImage(String idProduct, String productPictureId) {
        ProductEntity productEntity = productService.findProductById(idProduct);
        savePictureWithManualMode(productEntity);
        productPictureRepository.deleteById(productPictureId);
    }
}

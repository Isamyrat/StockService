package com.example.Task.service;

import com.example.Task.dto.ProductPictureDTO;

import java.util.List;

public interface ProductPictureService {
    String saveAccountImage(final String id, final String pictureLink);

    List<ProductPictureDTO> findAll(final Integer productId);
}

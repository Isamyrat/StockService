package com.example.Task.service;

public interface ProductPictureService {
    String saveAccountImage(final String id, final String pictureLink);
    void deleteImage(final String id, final String productPictureId);
}

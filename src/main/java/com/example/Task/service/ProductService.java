package com.example.Task.service;

import com.example.Task.dto.ProductDTO;
import com.example.Task.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    void saveProduct(final ProductDTO productDTO, final Integer idStock);

    Integer updateProduct(final ProductDTO productDTO, final String id);

    List<ProductDTO> findAllByProductData(final Integer pageNumber,
                                          final Integer rowPerPage,
                                          final String productData,
                                          final Integer stockId);

    ProductDTO findById(final String id);

    ProductEntity findProductById(final String id);

    void deleteById(final String id);

    void updateProductsInStockEveryThirteenMinutes();

    Long count();
}

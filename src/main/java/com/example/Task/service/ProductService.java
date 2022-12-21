package com.example.Task.service;

import com.example.Task.dto.OffersDTO;
import com.example.Task.dto.ProductDTO;
import com.example.Task.entity.ProductEntity;
import com.example.Task.entity.StockEntity;

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

    Long count();

    void deleteAllProductByStock(final StockEntity stockEntity);

    void saveAllProductFromXmlFeed(final OffersDTO offersDTO, final StockEntity stockEntity);

    OffersDTO senderToDao(final String xmlFilePath, final String feedLink, final String stockName);
}

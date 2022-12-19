package com.example.Task.service;

import com.example.Task.dto.StockDTO;
import com.example.Task.entity.StockEntity;

import java.util.List;

public interface StockService {
    List<StockDTO> findAll(final Integer pageNumber, Integer rowPerPage, final String name);

    void saveStock(final StockDTO stockDTO);

    void updateStock(final StockDTO stockDTO, final Integer id);

    StockDTO findById(final Integer id);

    void disableById(final Integer id);

    void enableById(final Integer id);

    StockEntity findStockById(final Integer id);

    List<StockEntity> findAllStockEntityWithFeed();

    Long count();
}

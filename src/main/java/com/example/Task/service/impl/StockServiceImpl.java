package com.example.Task.service.impl;

import com.example.Task.dto.StockDTO;
import com.example.Task.entity.StockEntity;
import com.example.Task.entity.enums.StockMode;
import com.example.Task.exception.BadRequestException;
import com.example.Task.exception.NotFoundException;
import com.example.Task.repository.StockRepository;
import com.example.Task.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    public static final String CAN_NOT_FIND_STOCK_WITH_THIS_ID = "Can not find stockList with this id: ";
    public static final String INVALID_DATE_FORMAT = "Invalid date format.";
    private final StockRepository stockRepository;

    @Override
    public List<StockDTO> findAll(Integer pageNumber, Integer rowPerPage, String name) {
        List<StockEntity> stockEntityList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        stockRepository.findAllByName(name, pageable).forEach(stockEntityList::add);
        return stockEntityList.stream()
            .map(this::mapStockToDTO)
            .collect(Collectors.toList());
    }


    @Override
    public void saveStock(final StockDTO stockDTO) {
        StockEntity stockEntity = fillingInStockData(stockDTO);
        stockRepository.save(stockEntity);
    }

    @Override
    public void updateStock(final StockDTO stockDTO, final Integer id) {
        final StockEntity stockEntity = findStockById(id);

        StockEntity stockEntitySecond = fillingInStockData(stockDTO);
        stockEntitySecond.setId(stockEntity.getId());
        stockEntitySecond.setActive(stockEntity.getActive());
        stockRepository.save(stockEntitySecond);
    }

    @Override
    public StockDTO findById(final Integer id) {
        StockEntity stockEntity = findStockById(id);
        return mapStockToDTO(stockEntity);
    }

    @Override
    public void disableById(final Integer id) {
        StockEntity stockEntity = findStockById(id);
        stockEntity.setActive(StockMode.DISABLED_MODE.getStr());
        stockRepository.save(stockEntity);
    }

    @Override
    public void enableById(final Integer id) {
        StockEntity stockEntity = findStockById(id);
        stockEntity.setActive(StockMode.ENABLED_MODE.getStr());
        stockRepository.save(stockEntity);
    }

    private StockEntity fillingInStockData(final StockDTO stockDTO) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setName(stockDTO.getName());
        stockEntity.setStartDate(parseStringToLocaleDateTime(stockDTO.getStartDate()));
        stockEntity.setEndDate(parseStringToLocaleDateTime(stockDTO.getEndDate()));
        stockEntity.setActive(StockMode.ENABLED_MODE.getStr());
        if (stockDTO.getFeedLink() != null) {
            stockEntity.setFeedLink(stockDTO.getFeedLink());
        }
        return stockEntity;
    }

    private StockDTO mapStockToDTO(final StockEntity stockEntity) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(stockEntity.getId());
        stockDTO.setName(stockEntity.getName());
        stockDTO.setStartDate(stockEntity.getStartDate().toString());
        stockDTO.setEndDate(stockEntity.getEndDate().toString());
        stockDTO.setActive(stockEntity.getActive());
        if (stockEntity.getFeedLink() != null) {
            stockDTO.setFeedLink(stockEntity.getFeedLink());
        }
        return stockDTO;
    }

    @Override
    public StockEntity findStockById(final Integer id) {
        return stockRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(CAN_NOT_FIND_STOCK_WITH_THIS_ID + id));
    }

    @Override
    public List<StockEntity> findAllStockEntityWithFeed() {
        return stockRepository.findAllByFeedLinkExists();
    }

    @Override
    public Long count() {
        return stockRepository.count();
    }

    private LocalDateTime parseStringToLocaleDateTime(final String time) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(time);
        }  catch (final DateTimeParseException e) {
            throw new BadRequestException(INVALID_DATE_FORMAT);
        }
        return localDateTime;
    }

}

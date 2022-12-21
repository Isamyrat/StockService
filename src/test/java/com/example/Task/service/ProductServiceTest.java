package com.example.Task.service;

import com.example.Task.controller.AbstractWebTests;
import com.example.Task.dto.OffersDTO;
import com.example.Task.entity.StockEntity;
import com.example.Task.repository.ProductRepository;
import com.example.Task.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest extends AbstractWebTests {

    public static final String FEED_LINK_OF_FIRST_STOCK =
        "https://export.admitad.com/ru/webmaster/websites/2090016/products/export_adv_products/?user=shaa80&code=nztwz8ig83&feed_id=21418&format=xml";
    public static final String PATH_TO_XML_FILE_IN_TEST = "src/test/resources/sax/";

    @Autowired
    private ProductService productService;

    @Autowired
    private  StockService stockService;

    @Test
    public  void testUpdateProductsInStockEveryThirteenMinutes(){
        final List<StockEntity> stockEntityList = stockService.findAllStockEntityWithFeed();
        assertNotNull(stockEntityList);
        assertEquals(FEED_LINK_OF_FIRST_STOCK, stockEntityList.get(0).getFeedLink());
        for(StockEntity stockEntity : stockEntityList){
            productService.deleteAllProductByStock(stockEntity);
            OffersDTO offersDTO = productService.senderToDao(PATH_TO_XML_FILE_IN_TEST, stockEntity.getFeedLink(), stockEntity.getName());
            productService.saveAllProductFromXmlFeed(offersDTO,stockEntity);
        }
    }
}

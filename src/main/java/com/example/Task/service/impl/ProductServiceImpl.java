package com.example.Task.service.impl;

import com.example.Task.dao.XmlParse;
import com.example.Task.dto.OfferDTO;
import com.example.Task.dto.OffersDTO;
import com.example.Task.dto.ProductDTO;
import com.example.Task.entity.ProductEntity;
import com.example.Task.entity.ProductPictureEntity;
import com.example.Task.entity.StockEntity;
import com.example.Task.entity.enums.ProductMode;
import com.example.Task.exception.NotFoundException;
import com.example.Task.repository.ProductPictureRepository;
import com.example.Task.repository.ProductRepository;
import com.example.Task.service.ProductService;
import com.example.Task.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public static final String CAN_NOT_FIND_PRODUCT_WITH_THIS_ID = "Can not find product with this id: ";
    public static final String PATH_OF_SAX_FILES = "src/main/resources/sax/";
    private final ProductRepository productRepository;
    private final ProductPictureRepository productPictureRepository;
    private final StockService stockService;
    private final XmlParse xmlParse;

    @Override
    public void saveProduct(final ProductDTO productDTO, final Integer idStock) {
        ProductEntity productEntity = fillingInProductData(productDTO);
        productEntity.setFlag(ProductMode.MANUAL_MODE.getStr());
        productEntity.setStock(stockService.findStockById(idStock));
        productRepository.save(productEntity);
    }

    @Override
    public Integer updateProduct(final ProductDTO productDTO, final String id) {
        final ProductEntity productEntity = findProductById(id);

        ProductEntity productEntitySecond = fillingInProductData(productDTO);
        productEntitySecond.setId(productEntity.getId());
        productEntitySecond.setFlag(ProductMode.MANUAL_MODE.getStr());
        productEntitySecond.setStock(productEntity.getStock());
        productRepository.save(productEntitySecond);
        return productEntity.getStock().getId();
    }

    @Override
    public List<ProductDTO> findAllByProductData(final Integer pageNumber,
                                                 final Integer rowPerPage,
                                                 final String productData,
                                                 final Integer stockId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        List<ProductEntity> productEntityList =
            new ArrayList<>(productRepository.findAllByProductData(stockId, productData, pageable));
        return productEntityList.stream()
            .map(this::mapProductToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(final String id) {
        ProductEntity productEntity = findProductById(id);
        return mapProductToDTO(productEntity);
    }

    @Override
    public ProductEntity findProductById(final String id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(CAN_NOT_FIND_PRODUCT_WITH_THIS_ID + id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    private ProductEntity fillingInProductData(final ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(parseFloat(productDTO.getPrice()));
        return productEntity;
    }

    private ProductDTO mapProductToDTO(final ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setFlag(productEntity.getFlag());
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice().toString());
        productDTO.setStockId(productEntity.getStock().getId());
        if (productEntity.getProductPictureEntities().size() > 0) {
            productDTO.setProductPictureEntities(productEntity.getProductPictureEntities());
        }
        return productDTO;
    }

    @Override
    public void deleteAllProductByStock(final StockEntity stockEntity) {
        productPictureRepository.deleteAllProductPicturesByStockId(stockEntity.getId(), ProductMode.AUTO_MODE.getStr());
        productRepository.deleteAllByStockIdAndFlag(stockEntity.getId(), ProductMode.AUTO_MODE.getStr());
    }

    @Transactional
    @Override
    public void saveAllProductFromXmlFeed(final OffersDTO offersDTO, final StockEntity stockEntity) {
        List<ProductEntity> productEntityList = new ArrayList<>();
        if(offersDTO.getOfferDTOList() != null) {
            for (OfferDTO offerDTO : offersDTO.getOfferDTOList()) {
                if (!productRepository.existsByName(offerDTO.getName())) {
                    productEntityList.add(mapProductByOffer(offerDTO, stockEntity.getId()));
                }
            }
        }
        productRepository.saveAll(productEntityList);
    }

    private ProductEntity mapProductByOffer(final OfferDTO offerDTO, final Integer stockId) {
        List<ProductPictureEntity> productPictureEntityList = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(offerDTO.getName());
        productEntity.setPrice(offerDTO.getPrice());
        productEntity.setFlag(ProductMode.AUTO_MODE.getStr());
        productEntity.setStock(stockService.findStockById(stockId));
        for (String imageLink : offerDTO.getLinkOfPicture()) {
            productPictureEntityList.add(mapProductPicture(imageLink, productEntity));
        }
        productEntity.setProductPictureEntities(productPictureEntityList);
        return productEntity;
    }

    private ProductPictureEntity mapProductPicture(String imageLink, ProductEntity productEntity) {
        ProductPictureEntity productPictureEntity = new ProductPictureEntity();
        productPictureEntity.setLinkOfImages(imageLink);
        productPictureEntity.setProduct(productEntity);
        return productPictureEntity;
    }

    @Scheduled(cron = "0 */13 * ? * *")
    public void updateProductsInStockEveryThirteenMinutes() {
        final List<StockEntity> stockEntityList = stockService.findAllStockEntityWithFeed();
        for (StockEntity stockEntity : stockEntityList) {
            log.info("-----------------------------------------------Start------------------------------------");
            log.info("Starting delete product.ftl with auto mode");
            deleteAllProductByStock(stockEntity);
            log.info("Deleted All product.ftl with auto mode");
            OffersDTO offersDTO = senderToDao(PATH_OF_SAX_FILES,stockEntity.getFeedLink(), stockEntity.getName());
            log.info("Starting to save offers");
            saveAllProductFromXmlFeed(offersDTO, stockEntity);
            log.info("Finished to save offers");
        }
    }

    @Transactional
    @Override
    public OffersDTO senderToDao(final String xmlFilePath,final String feedLink, final String stockName){
        return xmlParse.parser(feedLink, xmlFilePath, stockName);
    }

    @Override
    public Long count() {
        return productRepository.count();
    }

    private Float parseFloat(final String price) {
        return Float.parseFloat(price);
    }
}

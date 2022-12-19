package com.example.Task.repository;

import com.example.Task.entity.ProductPictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductPictureRepository extends JpaRepository<ProductPictureEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_pictures WHERE product_id IN (SELECT id FROM product WHERE stock_id =:id AND flag =:flag)", nativeQuery = true)
    void deleteAllProductPicturesByStockId(final Integer id,final String flag);

}
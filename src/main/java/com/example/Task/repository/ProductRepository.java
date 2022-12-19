package com.example.Task.repository;

import com.example.Task.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM  PRODUCT WHERE stock_id =:id AND flag =:flag", nativeQuery = true)
    void deleteAllByStockIdAndFlag(final Integer id, String flag);

    @Query(value = "select * from PRODUCT p where p.stock_id =:stockId AND p.name LIKE %:keyword%",nativeQuery = true)
    List<ProductEntity> findAllByProductData(final Integer stockId, final String keyword, Pageable pageable);

    Boolean existsByName(final String name);
}

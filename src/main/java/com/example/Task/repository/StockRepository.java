package com.example.Task.repository;

import com.example.Task.entity.StockEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, Integer> {
    @Transactional
    @Query(value = "SELECT * FROM STOCK s WHERE s.feed_link IS NOT NULL", nativeQuery = true)
    List<StockEntity> findAllByFeedLinkExists();

    @Transactional
    @Query(value = "select * from STOCK s WHERE s.name LIKE %:name%", nativeQuery = true)
    List<StockEntity> findAllByName(final String name, Pageable pageable);
}

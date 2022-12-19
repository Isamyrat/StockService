package com.example.Task.repository;

import com.example.Task.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select * from USERENTITY u WHERE u.fullname LIKE %:keyword% OR u.username LIKE %:keyword%",
        nativeQuery = true)
    List<UserEntity> findAllByFullNameLikeAndUsernameLike(final String keyword, Pageable pageable);
}

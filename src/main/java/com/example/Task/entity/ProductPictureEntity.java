package com.example.Task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "PRODUCT_PICTURES")
public class ProductPictureEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "PRODUCT_IMAGES_LINK")
    private String linkOfImages;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}

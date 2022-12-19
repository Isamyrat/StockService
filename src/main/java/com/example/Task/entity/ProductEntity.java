package com.example.Task.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Float price;

    // ручной режим
    @Column(name = "FLAG", nullable = false)
    private String flag;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stock;

    @OneToMany(targetEntity = ProductPictureEntity.class, mappedBy = "product", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<ProductPictureEntity> productPictureEntities;
}

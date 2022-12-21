package com.example.Task.dto;

import com.example.Task.entity.ProductPictureEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    public static final String PRODUCT_PRICE_CAN_NOT_BE_EMPTY = "Product price can not be empty.";
    public static final String PRODUCT_NAME_CAN_NOT_BE_EMPTY = "Product name can not be empty.";

    private String id;

    @NotBlank(message = PRODUCT_NAME_CAN_NOT_BE_EMPTY)
    private String name;

    private String flag;

    @NotBlank(message = PRODUCT_PRICE_CAN_NOT_BE_EMPTY)
    private String price;

    private Integer stockId;

    private List<ProductPictureEntity> productPictureEntities;
}

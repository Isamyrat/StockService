package com.example.Task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StockDTO {
    public static final String DATE_NOT_BE_EMPTY = "Date parameters should be not null.";
    public static final String STOCK_NAME_CAN_NOT_BE_EMPTY = "Stock name can not be empty.";

    private Integer id;

    @NotBlank(message = STOCK_NAME_CAN_NOT_BE_EMPTY)
    @Size(max = 200)
    private String name;

    @NotBlank(message = DATE_NOT_BE_EMPTY)
    private String startDate;

    @NotBlank(message = DATE_NOT_BE_EMPTY)
    private String endDate;

    @Size(max = 20)
    private String active;

    private String feedLink;
}


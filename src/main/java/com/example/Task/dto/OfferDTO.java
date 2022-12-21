package com.example.Task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class OfferDTO {
    private String name;
    private Float price;
    private List<String> linkOfPicture;

    public OfferDTO() {
        this.linkOfPicture = new ArrayList<>();
    }
}

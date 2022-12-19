package com.example.Task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class OfferDTO {
    private String name;
    private Float price;
    private List<String> linkOfPicture;

    public OfferDTO() {
        this.linkOfPicture = new ArrayList<>();
    }
}

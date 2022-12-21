package com.example.Task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class OffersDTO {
    private List<OfferDTO> offerDTOList;
}

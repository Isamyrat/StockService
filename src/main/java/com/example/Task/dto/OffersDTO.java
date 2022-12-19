package com.example.Task.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OffersDTO {
    private List<OfferDTO> offerDTOList;
}
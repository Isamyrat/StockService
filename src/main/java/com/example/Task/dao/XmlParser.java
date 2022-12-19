package com.example.Task.dao;

import com.example.Task.dto.OfferDTO;
import com.example.Task.dto.OffersDTO;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XmlParser extends DefaultHandler {
    private static final String OFFERS = "offers";
    private static final String OFFER = "offer";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PICTURE = "picture";
    private boolean flag = false;
    private OffersDTO offersDTO;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        offersDTO = new OffersDTO();
        flag = false;
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        if (Objects.equals(qName, OFFERS)) {
            flag = true;
        }
        if (flag) {
            switch (qName) {
                case OFFERS -> offersDTO.setOfferDTOList(new ArrayList<>());
                case OFFER -> offersDTO.getOfferDTOList().add(new OfferDTO());
                case NAME, PRICE, PICTURE -> elementValue = new StringBuilder();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (flag) {
            switch (qName) {
                case NAME -> latestArticle().setName(elementValue.toString());
                case PRICE -> latestArticle().setPrice(Float.valueOf(elementValue.toString()));
                case PICTURE -> latestArticle().getLinkOfPicture().add(elementValue.toString());
            }
        }
    }

    private OfferDTO latestArticle() {
        List<OfferDTO> articleList = offersDTO.getOfferDTOList();
        int latestArticleIndex = articleList.size() - 1;
        return articleList.get(latestArticleIndex);
    }

    public OffersDTO getWebsite() {
        return offersDTO;
    }
}

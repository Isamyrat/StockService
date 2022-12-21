package com.example.Task.dao;

import com.example.Task.dto.OffersDTO;

public interface XmlParse {
    OffersDTO parser(final String url,final String pathWhereNeedToSaveXmlFile, final String stockName);
}

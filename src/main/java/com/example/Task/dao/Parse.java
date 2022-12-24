package com.example.Task.dao;

import com.example.Task.dto.OffersDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
@Service
public class Parse implements XmlParse {

    @Override
    public OffersDTO parser(final String url, final String pathWhereNeedToSaveXmlFile, final String stockName) {

        final String PATH_XML = pathWhereNeedToSaveXmlFile + stockName + ".dtd";
        log.info("starting downloading");
        try {
            downloadUsingStream(url, PATH_XML);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("finished downloading");
        log.info("starting parsing");
        OffersDTO offersDTO = new OffersDTO();
        try {
            offersDTO = parseXmlStock(PATH_XML);
        } catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("finished parsing");
        log.info("Offer size is " + offersDTO.getOfferDTOList().size());
        return offersDTO;
    }

    private OffersDTO parseXmlStock(final String stockName)
        throws SAXException, ParserConfigurationException, IOException, InterruptedException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser saxParser = factory.newSAXParser();
        XmlParser xmlParser = new XmlParser();
        saxParser.parse(stockName, xmlParser);
        return xmlParser.getWebsite();
    }

    private void downloadUsingStream(final String urlStr, final String stockName)
        throws IOException, InterruptedException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(stockName);
        byte[] buffer = new byte[1024];
        int count;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
}

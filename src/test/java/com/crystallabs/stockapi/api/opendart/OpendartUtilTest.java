package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.api.opendart.corpcode.xml.CorporationCodeListTag;
import com.crystallabs.stockapi.api.opendart.corpcode.xml.CorporationCodeResultTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBException;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpendartUtilTest {

    private OpendartUtil opendartUtil = OpendartUtil.getInstance();

    @Test
    @DisplayName("xml 바이트 데이터를 xml 문서로 변환")
    public void byteArrayToDocumentTest() throws IOException {

        // Given
        File file = new File("test-data/CORPCODE.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();

        // When
        Document xmlDocs = opendartUtil.byteArrayToXmlDocs(bytes);

        // Then
        assertNotNull(xmlDocs);
        assertNotNull(xmlDocs.getDocumentElement());
    }

    @Test
    @DisplayName("CORPCODE.xml 문서 -> 리스트")
    public void documentToList() throws IOException, JAXBException {

        // Given
        File file = new File("test-data/CORPCODE.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // When
        CorporationCodeResultTag corporationCodeResultTag = opendartUtil.xmlToObject(CorporationCodeResultTag.class, inputStream);
        CorporationCodeListTag[] corporationCodeListTags = corporationCodeResultTag.getList();

        // Then
        assertEquals("다코", corporationCodeListTags[0].getCorpName());
    }
}
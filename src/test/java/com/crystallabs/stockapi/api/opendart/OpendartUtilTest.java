package com.crystallabs.stockapi.api.opendart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OpendartUtilTest {

    private OpendartUtil opendartUtil = OpendartUtil.getInstance();

    @Test
    @DisplayName("byte 배열을 xml 문서로 변환")
    public void byteArrayToDocumentTest() throws Exception {

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
    @DisplayName("byte 배열을 xml 문서로 변환 실패")
    public void byteArrayToDocumentFailTest() throws Exception {

        // Given
        File file = new File("test-data/fail-test.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();

        // When & Then
        assertThrows(SAXParseException.class, () -> {
            opendartUtil.byteArrayToXmlDocs(bytes);
        });
    }
}
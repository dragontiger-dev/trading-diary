package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.api.opendart.corpcode.xml.CorpCodeListTag;
import com.crystallabs.stockapi.api.opendart.corpcode.xml.CorpCodeResultTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CorpCodeTest {

    private OpendartUtil opendartUtil = OpendartUtil.getInstance();

    @Test
    @DisplayName("CORPCODE.xml 문서 -> 리스트")
    public void documentToList() throws IOException, JAXBException {

        // Given
        File file = new File("test-data/CORPCODE.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // When
        CorpCodeResultTag corpCodeResultTag = (CorpCodeResultTag) opendartUtil.xmlToObject(CorpCodeResultTag.class, inputStream);
        CorpCodeListTag[] corpCodeListTags = corpCodeResultTag.getList();

        // Then
        assertEquals("다코", corpCodeListTags[0].getCorpName());
    }
}

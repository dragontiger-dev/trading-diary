package com.crystallabs.stockapi.api.opendart;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpendartUtil {

    private OpendartUtil() {}

    private static class OpendartUtilHolder {
        private static final OpendartUtil OPENDART_UTIL = new OpendartUtil();
    }

    public static OpendartUtil getInstance() {
        return OpendartUtilHolder.OPENDART_UTIL;
    }

    /**
     * Byte 배열 데이터를 xml 문서로 변환
     * @param bytes 압축 해제 후 byte[]로 변환한 데이터
     * @return {@link Document} xml 문서
     */
    public Document byteArrayToXmlDocs(byte[] bytes) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Document xmlDocs = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

        return xmlDocs;
    }

    /**
     * XML 문서를 Java Object로 바인딩
     * @param boundClass 바인딩 할 클래스
     * @param inputStream Xml 문서 Stream
     * @return 바인딩된 객체
     */
    public Object xmlToObject(Class<?> boundClass, InputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(boundClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(inputStream);
    }
}

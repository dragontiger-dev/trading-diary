package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.config.OpendartProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Open Dart Api Test")
class OpendartTest {

    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("Json 공통 - 정상")
    public void opendartApiJsonTest() {

        // Given
        OpendartEnum.ApiType api = OpendartEnum.ApiType.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자

        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), opendartProperties.getKey());
        paramsMap.add(OpendartEnum.ParamType.CORP_CODE.getParam(), corpCode);

        // When
        OpendartResponse opendartResponse = Opendart.getInstance(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> body = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertAll(
            () -> assertNotNull(opendartResponse),
            () -> assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass()),
            () -> assertTrue(body.containsKey("status")),
            () -> assertEquals("000", body.get("status")),
            () -> assertTrue(body.containsKey("message")),
            () -> assertEquals("정상", body.get("message")),
            () -> assertTrue(body.containsKey("stock_name")),
            () -> assertEquals("삼성전자", body.get("stock_name")),
            () -> assertTrue(opendartResponse.getStatus().is2xxSuccessful())
        );
    }

    @Test
    @DisplayName("Json 공통 - key 오타")
    public void opendartApiJsonTestKeyTypo() {

        // Given
        OpendartEnum.ApiType api = OpendartEnum.ApiType.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), "0000");
        paramsMap.add(OpendartEnum.ParamType.CORP_CODE.getParam(), corpCode);

        // When
        OpendartResponse opendartResponse = Opendart.getInstance(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> body = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertAll(
            () -> assertNotNull(opendartResponse),
            () -> assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass()),
            () -> assertTrue(body.containsKey("status")),
            () -> assertEquals("010", body.get("status")),
            () -> assertTrue(body.containsKey("message")),
            () -> assertEquals("등록되지 않은 인증키입니다.", body.get("message")),
            () -> assertTrue(opendartResponse.getStatus().is2xxSuccessful())
        );
    }

    @Test
    @DisplayName("Json 공통 - key 값 누락")
    public void opendartApiJsonTestNoKey() {

        // Given
        OpendartEnum.ApiType api = OpendartEnum.ApiType.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartEnum.ParamType.CORP_CODE.getParam(), corpCode);
        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), "");

        // When
        OpendartResponse opendartResponse = Opendart.getInstance(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> body = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertAll(
            () -> assertNotNull(opendartResponse),
            () -> assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass()),
            () -> assertTrue(body.containsKey("status")),
            () -> assertEquals("100", body.get("status")),
            () -> assertTrue(body.containsKey("message")),
            () -> assertEquals("인증키가 누락되었습니다.", body.get("message")),
            () -> assertTrue(opendartResponse.getStatus().is2xxSuccessful())
        );
    }

    @Test
    @DisplayName("Json 공통 - 파라미터 누락")
    public void opendartApiJsonTestNoParameter() {

        // Given
        OpendartEnum.ApiType api = OpendartEnum.ApiType.COMPANY_JSON;
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), opendartProperties.getKey());

        // When
        OpendartResponse opendartResponse = Opendart.getInstance(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> body = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertAll(
            () -> assertNotNull(opendartResponse),
            () -> assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass()),
            () -> assertTrue(body.containsKey("status")),
            () -> assertEquals("100", body.get("status")),
            () -> assertTrue(body.containsKey("message")),
            () -> assertEquals("필수값(corp_code)이 누락되었습니다.", body.get("message")),
            () -> assertTrue(opendartResponse.getStatus().is2xxSuccessful())
        );
    }

    @Test
    @DisplayName("Xml 공통 - 정상")
    public void opendartApiXmlTest() throws IOException {

        // Given
        OpendartEnum.ApiType api = OpendartEnum.ApiType.COMPANY_XML;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), opendartProperties.getKey());
        paramsMap.add(OpendartEnum.ParamType.CORP_CODE.getParam(), corpCode);

        // When
        OpendartResponse opendartResponse = Opendart.getInstance(opendartProperties).callApiXml(api, paramsMap);
        Document document = (Document) opendartResponse.getBody();

        // Then
        assertAll(
            () -> assertNotNull(opendartResponse),
            () -> assertTrue(document.getDocumentElement().hasChildNodes()),
            () -> {
                NodeList childNodes = document.getDocumentElement().getChildNodes();
                assertAll(
                    () -> assertEquals("status", childNodes.item(0).getNodeName()),
                    () -> assertEquals("000", childNodes.item(0).getTextContent()),
                    () -> assertEquals("message", childNodes.item(1).getNodeName()),
                    () -> assertEquals("정상", childNodes.item(1).getTextContent()),
                    () -> assertEquals("stock_name", childNodes.item(5).getNodeName()),
                    () -> assertEquals("삼성전자", childNodes.item(5).getTextContent())
                );
            },
            () -> assertTrue(opendartResponse.getStatus().is2xxSuccessful())
        );
    }
}
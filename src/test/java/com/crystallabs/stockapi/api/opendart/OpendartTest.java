package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.config.OpendartProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
        String api = OpendartField.Api.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartField.Param.KEY, opendartProperties.getKey());
        paramsMap.add(OpendartField.Param.CORP_CODE, corpCode);

        // When
        OpendartResponse opendartResponse = new Opendart(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> map = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertNotNull(opendartResponse);
        assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass());
        assertTrue(map.containsKey("status"));
        assertEquals("000", map.get("status"));
        assertTrue(map.containsKey("message"));
        assertEquals("정상", map.get("message"));
        assertTrue(opendartResponse.getStatus().is2xxSuccessful());
    }

    @Test
    @DisplayName("Json 공통 - key 오타")
    public void opendartApiJsonTestKeyTypo() {

        // Given
        String api = OpendartField.Api.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartField.Param.KEY, "0000");
        paramsMap.add(OpendartField.Param.CORP_CODE, corpCode);

        // When
        OpendartResponse opendartResponse = new Opendart(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> map = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertNotNull(opendartResponse);
        assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass());
        assertTrue(map.containsKey("status"));
        assertEquals("010", map.get("status"));
        assertTrue(map.containsKey("message"));
        assertEquals("등록되지 않은 인증키입니다.", map.get("message"));
        assertTrue(opendartResponse.getStatus().is2xxSuccessful());
    }

    @Test
    @DisplayName("Json 공통 - key 값 누락")
    public void opendartApiJsonTestNoKey() {

        // Given
        String api = OpendartField.Api.COMPANY_JSON;
        String corpCode = "00126380";    // 삼성전자
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartField.Param.CORP_CODE, corpCode);
        paramsMap.add(OpendartField.Param.KEY, "");

        // When
        OpendartResponse opendartResponse = new Opendart(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> map = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertNotNull(opendartResponse);
        assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass());
        assertTrue(map.containsKey("status"));
        assertEquals("100", map.get("status"));
        assertTrue(map.containsKey("message"));
        assertEquals("인증키가 누락되었습니다.", map.get("message"));
        assertTrue(opendartResponse.getStatus().is2xxSuccessful());
    }

    @Test
    @DisplayName("Json 공통 - 파라미터 누락")
    public void opendartApiJsonTestNoParameter() {

        // Given
        String api = OpendartField.Api.COMPANY_JSON;
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

        paramsMap.add(OpendartField.Param.KEY, opendartProperties.getKey());

        // When
        OpendartResponse opendartResponse = new Opendart(opendartProperties).callApiJson(api, paramsMap);
        Map<?, ?> map = (Map<?, ?>) opendartResponse.getBody();

        // Then
        assertNotNull(opendartResponse);
        assertEquals(LinkedHashMap.class, opendartResponse.getBody().getClass());
        assertTrue(map.containsKey("status"));
        assertEquals("100", map.get("status"));
        assertTrue(map.containsKey("message"));
        assertEquals("필수값(corp_code)이 누락되었습니다.", map.get("message"));
        assertTrue(opendartResponse.getStatus().is2xxSuccessful());
    }
}
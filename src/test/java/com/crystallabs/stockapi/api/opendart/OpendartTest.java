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
class OpendartTest {


    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("Opendart API Json 공통 호출 - 정상")
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
        assertEquals(map.get("status"), "000");
        assertTrue(map.containsKey("message"));
        assertEquals(map.get("message"), "정상");
        assertTrue(opendartResponse.getStatus().is2xxSuccessful());
    }
}
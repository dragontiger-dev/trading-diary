package com.crystallabs.stockapi.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpendartPropertiesTest {

    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("Opendart Key 환경변수 정상 로드")
    public void opendartPropertiesLoadTest() {

        // Given & When
        String key = opendartProperties.getKey();

        // Then
        assertNotNull(key);
        assertEquals(System.getenv("OPENDART_KEY"), key);
    }
}
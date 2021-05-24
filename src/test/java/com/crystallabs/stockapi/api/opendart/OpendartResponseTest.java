package com.crystallabs.stockapi.api.opendart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OpendartResponseTest {

    @Test
    @DisplayName("OpendartResponse 생성 - 정상")
    public void OpendartResponseCreate() {

        // Given & When
        OpendartResponse opendartResponse = OpendartResponse.builder()
                .headers(HttpHeaders.EMPTY)
                .body(new HashMap<>())
                .status(HttpStatus.OK)
                .build();

        // Then
        assertNotNull(opendartResponse);
        assertNotNull(opendartResponse.getHeaders());
        assertNotNull(opendartResponse.getBody());
        assertNotNull(opendartResponse.getStatus());
    }

    @Test
    @DisplayName("OpendartResponse 생성 - header 필드가 null 이면 실패")
    public void OpendartResponseCreateHeaderNull() {

        // Then
        NullPointerException headerNullPointerException = assertThrows(NullPointerException.class, () -> {

            // Given & When
            OpendartResponse.builder().build();
        });
        assertEquals("headers is marked non-null but is null", headerNullPointerException.getMessage());
    }

    @Test
    @DisplayName("OpendartResponse 생성 - body 필드가 null 이면 실패")
    public void OpendartResponseCreateBodyNull() {

        // Then
        NullPointerException bodyNullPointerException = assertThrows(NullPointerException.class, () -> {

            // Given & When
            OpendartResponse.builder()
                    .headers(HttpHeaders.EMPTY)
                    .build();
        });
        assertEquals("body is marked non-null but is null", bodyNullPointerException.getMessage());
    }

    @Test
    @DisplayName("OpendartResponse 생성 - status 필드가 null 이면 실패")
    public void OpendartResponseCreateStatusNull() {

        // Then
        NullPointerException statusNullPointerException = assertThrows(NullPointerException.class, () -> {

            // Given & When
            OpendartResponse.builder()
                    .headers(HttpHeaders.EMPTY)
                    .body(new HashMap<>())
                    .build();
        });
        assertEquals("status is marked non-null but is null", statusNullPointerException.getMessage());
    }
}
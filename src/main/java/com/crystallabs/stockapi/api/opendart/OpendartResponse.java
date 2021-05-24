package com.crystallabs.stockapi.api.opendart;

import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
@Builder
public class OpendartResponse {

    @NonNull
    private HttpHeaders headers;

    @NonNull
    private Object body;

    @NonNull
    private HttpStatus status;
}

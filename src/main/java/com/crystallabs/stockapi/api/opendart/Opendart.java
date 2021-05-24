package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.config.OpendartProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class Opendart {

    private final OpendartProperties opendartProperties;

    public Opendart(OpendartProperties opendartProperties) {
        this.opendartProperties = opendartProperties;
    }

    /**
     * 반환 타입이 json 형태인 API 호출 메서드
     * @param api Open Dart API 명 {@link OpendartField.Api}
     * @param paramsMap API 호출 시 사용되는 매개변수 {@link OpendartField.Param}
     * @return {@link OpendartResponse} (body type : {@link Map})
     * @see <a href="https://opendart.fss.or.kr/guide/main.do?apiGrpCd=DS001">Open Dart 개발 가이드</a>
     */
    public OpendartResponse callApiJson(String api, MultiValueMap<String, String> paramsMap) {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(opendartProperties.getUrl())
                .pathSegment(api)
                .queryParams(paramsMap)
                .build();

        return new RestTemplate()
                .execute(uriComponents.toUri(), HttpMethod.GET, null, this::jsonExtractor);
    }

    private OpendartResponse jsonExtractor(ClientHttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = response.getBody();
        byte[] bytes = inputStream.readAllBytes();

        // JSON -> Map 파싱
        Map<?, ?> body = objectMapper.readValue(bytes, Map.class);

        return OpendartResponse.builder()
                .headers(response.getHeaders())
                .body(body)
                .status(response.getStatusCode())
                .build();
    }
}

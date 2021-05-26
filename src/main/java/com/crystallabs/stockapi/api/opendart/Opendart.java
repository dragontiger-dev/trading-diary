package com.crystallabs.stockapi.api.opendart;

import com.crystallabs.stockapi.config.OpendartProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

@Component
public class Opendart {

    private static OpendartProperties opendartProperties;
    private Opendart() {}

    private static class OpendartHolder {
        private static final Opendart OPENDART = new Opendart();
    }

    public static Opendart getInstance(OpendartProperties opendartProperties) {
        Opendart.opendartProperties = opendartProperties;
        return OpendartHolder.OPENDART;
    }

    /**
     * 반환 타입이 json 형태인 API 호출 메서드
     * @param api Open Dart API 명 {@link OpendartEnum.ApiType}
     * @param paramsMap API 호출 시 사용되는 매개변수 {@link OpendartEnum.ParamType}
     * @return {@link OpendartResponse} (body type : {@link Map})
     * @see <a href="https://opendart.fss.or.kr/guide/main.do?apiGrpCd=DS001">Open Dart 개발 가이드</a>
     */
    public OpendartResponse callApiJson(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) {
        URI apiUrl = opendartUrlBuilder(api, paramsMap);

        return new RestTemplate()
                .execute(apiUrl, HttpMethod.GET, null, this::jsonExtractor);
    }

    private OpendartResponse jsonExtractor(ClientHttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = response.getBody();
        byte[] bytes = inputStream.readAllBytes();

        // JSON -> Map 파싱
        Map<?, ?> body = objectMapper.readValue(bytes, Map.class);

        return commonExtractor(response, body);
    }

    /**
     * 반환 타입이 xml 형태인 API 호출 메서드
     * @param api Open Dart API 명 {@link OpendartEnum.ApiType}
     * @param paramsMap API 호출 시 사용되는 매개변수 {@link OpendartEnum.ParamType}
     * @return {@link OpendartResponse} (body type : {@link Document})
     * @see <a href="https://opendart.fss.or.kr/guide/main.do?apiGrpCd=DS001">Open Dart 개발 가이드</a>
     */
    public OpendartResponse callApiXml(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) throws IOException{
        URI apiUrl = opendartUrlBuilder(api, paramsMap);

        return new RestTemplate()
                .execute(apiUrl, HttpMethod.GET, null, this::xmlExtractor);
    }

    private OpendartResponse xmlExtractor(ClientHttpResponse response) throws IOException {
        Document body = null;

        // XML 파싱
        try {
             body = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response.getBody());
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return commonExtractor(response, body);
    }

    private URI opendartUrlBuilder(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) {
        return UriComponentsBuilder.fromHttpUrl(opendartProperties.getUrl())
                .pathSegment(api.getApi())
                .queryParams(paramsMap)
                .build()
                .toUri();
    }

    private OpendartResponse commonExtractor(ClientHttpResponse response, Object body) throws IOException {
        return OpendartResponse.builder()
                .headers(response.getHeaders())
                .body(body)
                .status(response.getStatusCode())
                .build();
    }
}

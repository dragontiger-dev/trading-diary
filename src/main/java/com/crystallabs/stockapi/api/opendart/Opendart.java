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
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Open Dart Api
 * @see <a href="https://opendart.fss.or.kr/guide/main.do?apiGrpCd=DS001">개발 가이드</a>
 */
@Component
public class Opendart {

    private final OpendartProperties opendartProperties;

    public Opendart(OpendartProperties opendartProperties) {
        this.opendartProperties = opendartProperties;
    }

    /**
     * 반환 타입이 json 형태인 API 호출 메서드
     * @param api Open Dart API 명 {@link OpendartEnum.ApiType}
     * @param paramsMap API 호출 시 사용되는 매개변수 {@link OpendartEnum.ParamType}
     * @return {@link OpendartResponse} (body type : {@link Map})
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
     */
    public OpendartResponse callApiXml(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) {
        URI apiUrl = opendartUrlBuilder(api, paramsMap);

        return new RestTemplate()
                .execute(apiUrl, HttpMethod.GET, null, this::xmlExtractor);
    }

    private OpendartResponse xmlExtractor(ClientHttpResponse response) throws IOException {
        Document body;

        // XML 파싱
        try {
            body = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response.getBody());
            return commonExtractor(response, body);
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 반환 타입이 Binary (Zip) 형태인 API 호출 메서드
     * @param api Open Dart API 명 {@link OpendartEnum.ApiType}
     * @param paramsMap API 호출 시 사용되는 매개변수 {@link OpendartEnum.ParamType}
     * @return {@link OpendartResponse}
     * (body type : {@link Map} <br>
     *  / Key(String) : 파일 명, Value(Byte[]) 압축해제된 파일 Byte 데이터)
     */
    public OpendartResponse callApiZipBinary(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) {
        URI apiUrl = opendartUrlBuilder(api, paramsMap);

        return new RestTemplate()
                .execute(apiUrl, HttpMethod.GET, null, this::zipBinaryExtractor);
    }

    private OpendartResponse zipBinaryExtractor(ClientHttpResponse response) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(response.getBody());
        ZipEntry zipEntry;
        Map<String, byte[]> zipData = new HashMap<>();

        // 압축된 파일이 있을 경우 내용을 Byte 배열에 담음.
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            zipData.put(zipEntry.getName(), zipInputStream.readAllBytes());
        }

        return commonExtractor(response, zipData);
    }

    // API Key 쿼리스트링을 포함한 url 생성
    private URI opendartUrlBuilder(OpendartEnum.ApiType api, MultiValueMap<String, String> paramsMap) {
        
        // Api Key 추가
        paramsMap.add(OpendartEnum.ParamType.KEY.getParam(), opendartProperties.getKey());
        
        return UriComponentsBuilder.fromHttpUrl(opendartProperties.getUrl())
                .pathSegment(api.getApi())
                .queryParams(paramsMap)
                .build()
                .toUri();
    }

    // API 응답 공통
    private OpendartResponse commonExtractor(ClientHttpResponse response, Object body) throws IOException {
        return OpendartResponse.builder()
                .headers(response.getHeaders())
                .body(body)
                .status(response.getStatusCode())
                .build();
    }
}

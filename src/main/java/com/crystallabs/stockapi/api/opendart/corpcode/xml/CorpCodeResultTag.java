package com.crystallabs.stockapi.api.opendart.corpcode.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Open Dart 고유번호 API 응답 결과로 파싱된 XML 데이터를 바인딩하기 위한 최상위 객체
 *
 * <p><br>xml 형식</p>
 * {@code <result>} <br>
 * {@code - <list>...</list>} <br>
 * {@code - <list>...</list>} <br>
 * {@code - ...} <br>
 * {@code </result>} <br>
 */
@XmlRootElement(name = "result")
@Getter @Setter @ToString
public class CorpCodeResultTag {
    private CorpCodeListTag[] list;
}



package com.crystallabs.stockapi.api.opendart.corpcode.xml;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

/**
 * Open Dart 고유번호 API 응답 결과로 파싱된 XML 데이터를 바인딩하기 위한 객체
 *
 * <p><br>xml 형식</p>
 * {@code <list>} <br>
 * {@code - <corp_code>01234567</corp_code>} <br>
 * {@code - <corp_name>}주식회사{@code </corp_name>} <br>
 * {@code - <stock_code>012345</stock_code>} <br>
 * {@code - <modify_date>20170630</modify_date>} <br>
 * {@code </list>} <br>
 *
 * @see CorpCodeResultTag
 */
@Getter @ToString
public class CorpCodeListTag
{
    @XmlElement(name = "corp_code")
    private String corpCode;

    @XmlElement(name = "corp_name")
    private String corpName;

    @XmlElement(name = "stock_code")
    private String stockCode;

    @XmlElement(name = "modify_date")
    private String modifyDate;
}
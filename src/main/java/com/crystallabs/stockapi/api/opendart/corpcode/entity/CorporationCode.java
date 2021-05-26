package com.crystallabs.stockapi.api.opendart.corpcode.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "corporation_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CorporationCode {

    @Id @GeneratedValue
    private long id;

    @NonNull
    private String corpCode;    // 고유번호

    @NonNull
    private String corpName;    // 정식명칭

    @NonNull
    private String stockCode;   // 종목코드

    @NonNull
    private String modifyDate;  // 최종변경일자
}
package com.crystallabs.stockapi.api.opendart;

import lombok.Getter;

/**
 * @see <a href="https://opendart.fss.or.kr/guide/main.do?apiGrpCd=DS001">Open Dart API 개발 가이드</a>
 */
public class OpendartEnum {

    @Getter
    enum ApiType {

        /**
         * 고유번호 <br>
         * 반환 타입 : Zip FILE (binary)
         * 압축 해제 후 : xml
         */
        CORP_CODE("corpCode.xml"),

        /**
         * 기업개황 <br>
         * 반환 타입 : json
         */
        COMPANY_JSON("company.json"),

        /**
         * 기업개황 <br>
         * 반환 타입 : xml
         */
        COMPANY_XML("company.xml");

        private String api;

        ApiType(String api) {
            this.api = api;
        }
    }

    @Getter
    enum ParamType {

        /**
         * 발급받은 인증키(40자리)
         */
        KEY("crtfc_key"),

        /**
         * 공시대상회사의 고유번호(8자리)
         */
        CORP_CODE("corp_code"),

        /**
         * 검색시작 접수일자(YYYYMMDD) : 없으면 종료일(end_de)
         * 고유번호(corp_code)가 없는 경우 검색기간은 3개월로 제한
         */
        BEGIN_DATE("bgn_de"),

        /**
         * 검색종료 접수일자(YYYYMMDD) : 없으면 당일
         */
        END_DATE("end_de"),

        /**
         * 최종보고서만 검색여부(Y or N) 기본값 : N
         * (정정이 있는 경우 최종정정만 검색)
         */
        LAST_REPORT_AT("last_reprt_at"),

        /**
         * 공시 유형 (아래 링크 참조)
         * @see <a href="https://opendart.fss.or.kr/guide/detail.do?apiGrpCd=DS001&apiId=2019001">공시 유형</a>
         */
        PUBLIC_NOTIFICATION_TYPE("pblntf_ty"),

        /**
         * 공시 상세 유형 (아래 링크 참조)
         * @see <a href="https://opendart.fss.or.kr/guide/detail.do?apiGrpCd=DS001&apiId=2019001">공시 상세 유형</a>
         */
        PUBLIC_NOTIFICATION_DETAIL_TYPE("pblntf_detail_ty"),

        /**
         * 법인구분 : Y(유가), K(코스닥), N(코넥스), E(기타)
         * ※ 없으면 전체조회, 복수조건 불가
         */
        CORPORATION_CLASS("corp_cls"),

        /**
         * 접수일자: date
         * 회사명 : crp
         * 보고서명 : rpt
         * 기본값 : date
         */
        SORT("sort"),

        /**
         * 오름차순(asc), 내림차순(desc) 기본값 : desc
         */
        SORT_METHOD("sort_mth"),

        /**
         * 페이지 번호(1~n) 기본값 : 1
         */
        PAGE_NUMBER("page_no"),

        /**
         * 페이지당 건수(1~100) 기본값 : 10, 최대값 : 100
         */
        PAGE_COUNT("page_count");

        private String param;

        ParamType(String param) {
            this.param = param;
        }
    }
}
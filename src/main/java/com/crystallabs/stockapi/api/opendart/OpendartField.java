package com.crystallabs.stockapi.api.opendart;

public class OpendartField {

    /**
     * API 목록
     */
    public static class Api {

        /**
         * 기업개황 <br>
         * 반환 타입 : json
         * @see <a href="https://opendart.fss.or.kr/guide/detail.do?apiGrpCd=DS001&apiId=2019002">문서</a>
         */
        public static final String COMPANY_JSON = "company.json";

        /**
         * 기업개황 <br>
         *
         * 반환 타입 : xml
         * @see <a href="https://opendart.fss.or.kr/guide/detail.do?apiGrpCd=DS001&apiId=2019002">문서</a>
         * @serialField
         */
        public static final String COMPANY_XML  = "company.xml";
    }

    public static class Param {

        /**
         * 발급받은 인증키(40자리)
         */
        public static final String KEY = "crtfc_key";

        /**
         * 공시대상회사의 고유번호(8자리)
         */
        public static final String CORP_CODE = "corp_code";
    }
}

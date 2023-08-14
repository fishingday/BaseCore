package kr.co.basedevice.corebase.domain.code;

public enum WriteMakrCd {

    /* 로그인 성공(form) */
    LOGIN_SUCCESS_FORM

    /* 로그인 실패 */
    ,LOGIN_FAIL_FORM

    /* 로그인 성공(ajax) */
    ,LOGIN_SUCCESS_AJAX

    /* 로그인 실패(ajax) */
    ,LOGIN_FAIL_AJAX

    /* 접근 거부 */
    ,ACCESS_DENIED

    /* 로그아웃 성공 */
    ,LOGOUT_SUCCESS

    /* 서버 내부 오류 */
    ,INTERNAL_SERVER_ERROR

    /* 기타 오류 */
    ,OTHER_ERRROR

}
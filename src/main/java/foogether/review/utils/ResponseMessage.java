package foogether.review.utils;

public class ResponseMessage {

    /**
     * default
     */
    public static final String AUTHORIZED = "인증 성공";
    public static final String UNAUTHORIZED = "인증 실패";
    public static final String FORBIDDEN = "인가 실패";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String SERVICE_UNAVAILABLE = "현재 서비스를 사용하실 수 없습니다. 잠시후 다시 시도해 주세요.";

    public static final String DB_ERROR = "데이터베이스 에러";


    /* 리뷰 */
    public static final String WRONG_STATUS = "카테고리 설정이 잘못되었습니다.";

    public static final String READ_ALL_BUT_ZERO = "작성된 리뷰가 없습니다.";

    public static final String READ_ALL_REVIEW = "모든 리뷰 조회 성공";

    public static final String DELETE_CONTENT = "리뷰 삭제 성공";

    public static final String FAIL_DELETE_NULL = "해당 리뷰가 존재하지 않습니다.";

    public static final String REVIEW_CREATE = " 리뷰 생성 성공";

    public static final String FAIL_REVISE_REVIEW = "리뷰 수정 실패";

    public static final String UPDATE_REVIEW = "리뷰 수정 완료";

    public static final String FAIL_UPDATE_REVIEW = "리뷰 수정 실패";

    public static final String NOT_FOUND_LIST = "리뷰 탐색 실패";

    public static final String READ_REVIEW = "리뷰 상세 조회 성공";





}

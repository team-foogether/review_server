package foogether.review.service;

import foogether.review.domain.Entity.Active;
import foogether.review.domain.Entity.ServiceCategory;
import foogether.review.web.dto.DefaultResponse;
import foogether.review.web.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    /*
    * DONE : 1) DetailReview Create 구현  -> save
    *        2) DetailReview Delete 구현 -> delete
    *        3) 특정 DetailReview Read 구현 -> findByIdx
    *        4) DetailReview update -> save
             5) 전체 조회 구현 ->findAll
             *
      TODO : JWT 토큰 적용 -> header

    * */
    /* 전체 조회 */
    DefaultResponse<List<ReviewDto>> findAll(int writerIdx, ServiceCategory serviceCategory
            , String header) throws  Exception;
   // DefaultResponse<List<ReviewDto>> findAllbyActive (Active active, int writerIdx, ServiceCategory serviceCategory) throws  Exception;

    /* 리뷰 생성, 및 수정 */
    DefaultResponse saveReview(ReviewDto reviewDto, String header) throws Exception;

    /* 리뷰 삭제 */
    DefaultResponse deleteReview(int reviewIdx, String header);

    /* 특정 리뷰 조회 */
    DefaultResponse<ReviewDto> findByIdx(int reviewIdx, String header) throws  Exception;


}

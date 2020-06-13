package foogether.review.service;

import foogether.review.client.UserClient;
import foogether.review.domain.Entity.Active;
import foogether.review.domain.Entity.Review;
import foogether.review.domain.Entity.ServiceCategory;
import foogether.review.repository.ReviewRepository;
import foogether.review.utils.ResponseMessage;
import foogether.review.web.dto.DefaultResponse;
import foogether.review.web.dto.MeetingResponseDto;
import foogether.review.web.dto.ReviewDto;
import foogether.review.repository.*;
import lombok.RequiredArgsConstructor;
import foogether.review.web.dto.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.ws.Response;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
TODO : jwt 토큰 적용하기
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private final UserClient userClient;

    @Autowired
    private final JwtService jwtService;

    /* 전체 조회 : 카테고리별로 ( meeting, product, spaceshared) */
    @Override
    @Transactional
    public DefaultResponse<List<ReviewDto>> findAll(int writerIdx, ServiceCategory serviceCategory
            , String header) throws Exception {
        List<Review> reviewList;
        // reviewList = reviewRepository.findAllByWriterIdxAndServiceCategory(writerIdx, ServiceCategory);

        if (serviceCategory.equals(ServiceCategory.MEETING)) {
            reviewList = reviewRepository.findAllByActiveAndWriterIdxAndServiceCategory(Active.ACTIVE, writerIdx, ServiceCategory.MEETING);
        } else if (serviceCategory.equals(ServiceCategory.PRODUCT)) {
            reviewList = reviewRepository.findAllByActiveAndWriterIdxAndServiceCategory(Active.ACTIVE, writerIdx, ServiceCategory.PRODUCT);
        } else if (serviceCategory.equals(ServiceCategory.SPACESHARED)) {
            reviewList = reviewRepository.findAllByActiveAndWriterIdxAndServiceCategory(Active.ACTIVE, writerIdx, ServiceCategory.SPACESHARED);
        } else {
            return DefaultResponse.res("fail", ResponseMessage.WRONG_STATUS);
        }

        int numReview = reviewList.size();
        if (numReview == 0) {
            return DefaultResponse.res("success", ResponseMessage.READ_ALL_BUT_ZERO);
        }

        return DefaultResponse.res("success", numReview, ResponseMessage.READ_ALL_REVIEW, reviewList.stream().map(
                reviews -> {
                    ReviewDto reviewDto = new ReviewDto(reviews);
                    return reviewDto;
                }).collect(Collectors.toList()));

    }


    /*리뷰 삭제 */
    @Override
    @Transactional
    public DefaultResponse deleteReview(int reviewIdx, String header) {
        Review review = reviewRepository.findByIdx(reviewIdx);
        if (review == null) {
            return DefaultResponse.res("success", ResponseMessage.FAIL_DELETE_NULL);
        }

        ReviewDto reviewDto = new ReviewDto(review);

        if (jwtService.decode(header).getUserIdx() == reviewDto.getWriterIdx()) {
            reviewDto.setActive(Active.UNACTIVE);
            reviewRepository.save(reviewDto.toEntity());
            return DefaultResponse.res("success", ResponseMessage.DELETE_CONTENT);
        } else {
            return DefaultResponse.res("fail", ResponseMessage.UNAUTHORIZED);
        }

    }


    /* 리뷰 수정 및 저장 */
    @Transactional
    @Override
    public DefaultResponse saveReview(
            ReviewDto reviewDto, String header) throws Exception {
        if (reviewDto.getIdx() == 0) {
            // 처음 생성하는 경우이다.
            return DefaultResponse.res("success", ResponseMessage.REVIEW_CREATE);

        } else {
            // 수정하는 경우
            // 내용을 담아 놓고
            if (jwtService.decode(header).getUserIdx() == reviewDto.getWriterIdx()) {
                Review review = reviewRepository.findByIdx(reviewDto.getIdx());
                if (review == null) { // 없는  idx
                    return DefaultResponse.res("success", ResponseMessage.FAIL_REVISE_REVIEW);

                }
                if (review.getActive() == Active.ACTIVE) {
                    reviewRepository.save(reviewDto.toEntity());
                    return DefaultResponse.res("success", ResponseMessage.UPDATE_REVIEW);

                }
                return DefaultResponse.res("fail", ResponseMessage.FAIL_UPDATE_REVIEW);
            } else {
                return DefaultResponse.res("fail", ResponseMessage.UNAUTHORIZED);
            }
        }
    }

        /* 특정 게시물 상세 조회 */


        @Transactional
        @Override
        public DefaultResponse<ReviewDto> findByIdx(int reviewIdx, String header) throws Exception {

            try {

                Review review = reviewRepository.findByIdxAndActive(reviewIdx, Active.ACTIVE);

                if (review == null) {
                    return DefaultResponse.res("fail", ResponseMessage.NOT_FOUND_LIST);
                }

                ReviewDto reviewDto = new ReviewDto(review);

                log.info("reviewIdx >>> " +reviewDto.getIdx());

                int myIdx = jwtService.decode(header).getUserIdx();
                ResponseEntity<DefaultResponse<UserResponseDto>>
                        myInfo = userClient.getUserInfo(myIdx);


                UserResponseDto myDto = new UserResponseDto(myInfo.getBody().getData());

                if (myDto.getIdx() == reviewDto.getWriterIdx()) {

                    reviewDto.setAuth(true);
                }


                return DefaultResponse.res("success", 1, ResponseMessage.READ_REVIEW
                        , reviewDto);

            } catch (Exception e) {
                return DefaultResponse.res("fail", ResponseMessage.INTERNAL_SERVER_ERROR);
            }
        }

    }

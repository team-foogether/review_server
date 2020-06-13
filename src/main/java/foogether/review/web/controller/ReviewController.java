package foogether.review.web.controller;

import foogether.review.domain.Entity.ServiceCategory;
import foogether.review.service.JwtService;
import foogether.review.service.ReviewService;
import foogether.review.utils.ResponseMessage;
import foogether.review.utils.auth.Auth;
import foogether.review.web.dto.DefaultResponse;
import foogether.review.web.dto.ReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static foogether.review.web.dto.DefaultResponse.FAIL_DEFAULT_RES;

@Slf4j
@RequestMapping("/review")
@CrossOrigin("*")
@RestController // 결과값을 json 으로 출력함
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @Autowired
    private JwtService jwtService;


    // 한 사용자가 작성한 리뷰 전체 조회
    @CrossOrigin("*")
    @GetMapping(value = "/{offset}/{writerIdx}/{serviceCategory}")
    public ResponseEntity findAll(@RequestHeader(value = "Authorization", required = false)
           final String header,
            @PathVariable("offset") int limit,
            @PathVariable("writerIdx") int writerIdx,
            @PathVariable("serviceCategory") ServiceCategory serviceCategory

    ){

        DefaultResponse<List<ReviewDto>> defaultResponse;


        try{

            defaultResponse = reviewService.findAll(writerIdx, serviceCategory,header);
            //System.out.println("controller 에러");
            //System.out.println(defaultResponse);
            return new ResponseEntity<> (defaultResponse, HttpStatus.OK);
        }catch (Exception e){
            defaultResponse = DefaultResponse.res("fail", ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<> (defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*리뷰 삭제 */
    @DeleteMapping(value ="/{reviewIdx}")
    public ResponseEntity deleteReview(
            @RequestHeader(value="Authorization") final String header,
            @PathVariable("reviewIdx") int reviewIdx
    ){
        DefaultResponse<ReviewDto> defaultResponse;
        try{
            defaultResponse = reviewService.deleteReview(reviewIdx,header);
            return new ResponseEntity(defaultResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /* 리뷰 수정 및 저장 */
    @Auth
    @PostMapping(value = "")
    public ResponseEntity saveReview(
            @RequestHeader(value = "Authorization") final String header,
            ReviewDto reviewDto)
    {
        DefaultResponse<ReviewDto> defaultResponse;
        try{
            defaultResponse = reviewService.saveReview(reviewDto,header);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(FAIL_DEFAULT_RES,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /* 특정 게시물 상세 조회 */
    @GetMapping("/{reviewIdx}")
    public ResponseEntity findAllByIdx(@RequestHeader(value = "Authorization", required = false) final String header,
                                       @PathVariable("reviewIdx") int reviewIdx){
        DefaultResponse<ReviewDto> defaultResponse;

        try{
            defaultResponse = reviewService.findByIdx(reviewIdx,header);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

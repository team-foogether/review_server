package foogether.review.web.dto;

import foogether.review.domain.Entity.Active;
import foogether.review.domain.Entity.Review;
import foogether.review.domain.Entity.ServiceCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    // 리뷰 idx
    private int idx;

    // 리뷰 제목
    private String title;

    // 리뷰 내용
    private String content;

    // 리뷰 작성자
    private int writerIdx;

    // 리뷰 별점
    private double score;

    // 보드 인덱스
    private int boardIdx;

    private boolean auth;

    // 서비스 카테고리
    private ServiceCategory serviceCategory;

    private Active active;

    // entity -> dto
    public ReviewDto(Review entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.boardIdx = entity.getBoardIdx();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.writerIdx = entity.getWriterIdx();
        this.serviceCategory = entity.getServiceCategory();
        this.active = entity.getActive();
    }

    // dto -> Entity
    public Review toEntity(){
        return Review.builder()
                .idx(this.idx)
                .boardIdx(this.boardIdx)
                .content(this.content)
                .title(this.title)
                .writerIdx(this.writerIdx)
                .score(this.score)
                .serviceCategory(this.serviceCategory)
                .active(this.active)
                .build();

    }


}

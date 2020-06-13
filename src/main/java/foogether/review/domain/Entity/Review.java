package foogether.review.domain.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.DynamicInsert;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="Review")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_idx")
    private int idx;

    @Column(name = "review_title")
    private String title;

    @Column(name = "review_content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_category")
    private ServiceCategory serviceCategory;

    @Column(name = "writer_idx")
    private int writerIdx;

    @Column(name = "review_score")
    private double score;

    @Column(name = "review_createdDate")
    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name="review_modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // 보드 인덱스 내용 -> 원글 내용

    @Enumerated(EnumType.STRING)
    @Column(name = "Active")
    private Active active;


    private int boardIdx;

    @PrePersist
    public void prePersist() {
        this.active = this.active == null ? Active.ACTIVE : this.active;

    }

    @PreUpdate
    public void preUpdate() {
        this.active = this.active == null ? Active.ACTIVE : this.active;

    }


    @Builder
    public Review(int idx, String title, String content, int writerIdx, double score,
                  LocalDateTime modifiedDate, int boardIdx, ServiceCategory serviceCategory,Active active){
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.writerIdx = writerIdx;
        this.score = score;
        this.modifiedDate = modifiedDate;
        this.boardIdx = boardIdx;
        this.serviceCategory = serviceCategory;
        this.active = active;

    }
}

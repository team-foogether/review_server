package foogether.review.repository;

import foogether.review.domain.Entity.Active;
import foogether.review.domain.Entity.Review;
import foogether.review.domain.Entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface  ReviewRepository extends JpaRepository<Review, Integer> {


    /* 한사람이 작성한 리뷰 전체 조회 구현*/
    List<Review> findAllByWriterIdx(int writerIdx);

    /* 한 사람이 작성한 리뷰 중, 카테고리 별로 조회 구현 */
    // @Query("SELECT m FROM Review m WHERE writer_idx = :writerIdx and service_category = :serviceCategory")
    List<Review> findAllByActiveAndWriterIdxAndServiceCategory(@Param("active") Active active,@Param("writerIdx")
            int writerIdx, @Param("serviceCategory")
            ServiceCategory serviceCategory);


    /* 한사람이 작성한 리뷰 삭제 여부 조회 */
    Review findByIdx(int reviewIdx);

    /* 한 사람이 작성한 리뷰 조회 */
    Review findByIdxAndActive(int reviewIdx, Active active);





}

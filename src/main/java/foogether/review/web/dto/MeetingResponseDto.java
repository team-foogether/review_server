package foogether.review.web.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Meeting service 에서 필요한 정보를 feign client로 받기 위한 DTO
 */
@Data
@NoArgsConstructor
public class MeetingResponseDto {

    private int boardIdx;

    // 작성자 정보
    private int writerIdx; // user_id

    private String image;

    private String originTitle;

    public MeetingResponseDto(MeetingResponseDto data){
        this.boardIdx = data.getBoardIdx();
        this.writerIdx = data.getWriterIdx();
        this.image = data.getImage();
        this.originTitle = data.getOriginTitle();


    }

}

package foogether.review.client;

//import foogether.meetings.web.dto.DefaultResponse;
//import foogether.meetings.web.dto.OwnerDto;
//import foogether.meetings.web.dto.UserResponseDto;
import foogether.review.web.dto.DefaultResponse;
import foogether.review.web.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@Component
// Hsytrix -> fallback method
//@FeignClient(url="http://localhost:8082", name = "user-service")
@FeignClient(url="http://foogether.us:8080", name = "user-service")
public interface UserClient {

    @PostMapping("/users/list")
    ResponseEntity<DefaultResponse<List<UserResponseDto>>> getUserList(
            @RequestBody List<Integer> UserIdxList);

    @GetMapping("/users/{userIdx}")
    ResponseEntity<DefaultResponse<UserResponseDto>> getUserInfo(

            @PathVariable("userIdx") int userIdx);

}
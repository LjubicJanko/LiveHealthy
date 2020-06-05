package live.healthy.controller;


import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.SubmitDto;
import live.healthy.service.plan.IntakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/intake")
@RequiredArgsConstructor
public class IntakeController {

    private final IntakeService intakeService;

    @PostMapping("/submit/{userId}")
    public ResponseEntity submit(@PathVariable Long userId, @RequestBody SubmitDto submitDto) {

        try {
            intakeService.submit(userId, submitDto.getDayIndex(), submitDto.getCaloriesDifference());
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>(userNotFound.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

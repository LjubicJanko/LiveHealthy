package live.healthy.controller;


import live.healthy.exception.user.UserNotFound;
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

    @PostMapping("/submit/{userId}/{dayIndex}")
    public ResponseEntity submit(@PathVariable Long userId, @PathVariable int dayIndex, @RequestBody double caloriesDifference) {

        try {
            intakeService.submit(userId, dayIndex, caloriesDifference);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>(userNotFound.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

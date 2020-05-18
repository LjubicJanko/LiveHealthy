package live.healthy.controller;

import live.healthy.exception.user.UserNotFound;
import live.healthy.service.basic.BasicDeterminationService;
import live.healthy.service.plan.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/create/{userId}")
    public ResponseEntity createPlan(@PathVariable Long userId, @RequestBody List<String> forbiddenFood) {
        try {
            planService.createPlan(userId, forbiddenFood);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>(userNotFound.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


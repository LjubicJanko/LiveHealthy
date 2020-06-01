package live.healthy.controller;

import live.healthy.exception.plan.NutritionPlanAlreadyExists;
import live.healthy.exception.plan.NutritionPlanNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.CreatePlanInfoDto;
import live.healthy.service.plan.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/create/{userId}")
    public ResponseEntity createPlan(@PathVariable Long userId, @RequestBody CreatePlanInfoDto createPlanInfoDto) {
        try {
            return new ResponseEntity<>(planService.createPlan(userId, createPlanInfoDto), HttpStatus.OK);
        } catch (UserNotFound | NutritionPlanAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nutrition/{id}")
    public ResponseEntity getNutritionPlan(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(planService.getNutritionPlan(id), HttpStatus.OK);
        } catch (NutritionPlanNotFound nutritionPlanNotFound) {
            return new ResponseEntity<>(nutritionPlanNotFound.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


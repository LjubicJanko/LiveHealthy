package live.healthy.controller;


import live.healthy.facts.dto.CreateBodyTypeRuleDto;
import live.healthy.facts.dto.CreateRuleDto;
import live.healthy.service.dynamic.DynamicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/dynamic")
@RequiredArgsConstructor
public class DynamicRulesController {

    private final DynamicService dynamicService;


    @PostMapping("/bodyType")
    public ResponseEntity createBodyTypeRule(@RequestBody CreateBodyTypeRuleDto createBodyTypeRuleDto) {
        try {

            return new ResponseEntity<>(dynamicService.createBodyTypeRule(createBodyTypeRuleDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

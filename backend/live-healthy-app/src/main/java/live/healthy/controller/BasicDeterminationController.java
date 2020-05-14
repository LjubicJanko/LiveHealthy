package live.healthy.controller;

import live.healthy.service.basic.BasicDeterminationService;
import live.healthy.service.basic.BasicDeterminationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import live.healthy.facts.dto.BodyInfoDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/basic")
@RequiredArgsConstructor
public class BasicDeterminationController {
	
	private BasicDeterminationService basicDeterminationService;

	@Autowired
	public BasicDeterminationController(BasicDeterminationServiceImpl basicDeterminationService) {
		this.basicDeterminationService = basicDeterminationService;
	}
	@PostMapping("/determine")
	public ResponseEntity determine(@RequestBody BodyInfoDto bodyInfoDto){
		return new ResponseEntity(basicDeterminationService.determine(bodyInfoDto), HttpStatus.OK);
	}
}

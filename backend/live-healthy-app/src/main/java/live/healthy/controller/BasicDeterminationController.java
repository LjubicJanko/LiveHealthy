package live.healthy.controller;

import live.healthy.exception.user.BodyTypeNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.service.basic.BasicDeterminationService;
import live.healthy.service.basic.BasicDeterminationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PostMapping("/determine/{userId}")
	public ResponseEntity determine(@RequestBody BodyInfoDto bodyInfoDto, @PathVariable Long userId){
		try {
			return new ResponseEntity<>(basicDeterminationService.determine(bodyInfoDto, userId), HttpStatus.OK);
		} catch (BodyTypeNotFound | UserNotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

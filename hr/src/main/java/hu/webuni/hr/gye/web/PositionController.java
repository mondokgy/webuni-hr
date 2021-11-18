package hu.webuni.hr.gye.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.PositionDto;
import hu.webuni.hr.gye.mapper.PositionMapper;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.service.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
	private static final Logger log = LoggerFactory.getLogger("LOG");

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private PositionMapper positionMapper;
	
	@GetMapping
	public List<PositionDto> getAll(){


		log.debug("restapi controller Position, /, get, getAll start");
		List<PositionDto> allPosition = positionMapper.positionToDto(positionService.findAll());
		
		return allPosition;			
	}
	
	@PostMapping
	public PositionDto createPosition(@Valid @RequestBody PositionDto positionDto) {
		
		log.debug("restapi controller Position, /, post, createPosition start");		
		Position position = positionService.save(positionMapper.dtoToPosition(positionDto));
		log.debug("restapi controller Position, /, post, createPosition end");
		
		return  positionMapper.positionToDto(position);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PositionDto> deletePosition(@PathVariable Long id) {
		
		log.debug("restapi controller Position, /{id}, delete, deletePosition start");
		try {
			PositionDto positionDto = positionMapper.positionToDto(positionService.delete(id));
			log.debug("restapi controller, /{id}, delete, deletePosition end");
			return ResponseEntity.ok(positionDto);
		}catch (NoSuchElementException e) {
			log.debug("Invalid input: positionDto.");
			log.debug("restapi controller Position, /{id}, delete, deletePosition end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
	}
}

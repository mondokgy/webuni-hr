package hu.webuni.hr.gye.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.HolidaysDto;
import hu.webuni.hr.gye.mapper.HolidaysMapper;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.service.HolidaysService;

@RestController
@RequestMapping("/api/holiday")
public class HolidaysController {

	@Autowired
	HolidaysMapper holidaysMapper;
	
	@Autowired
	HolidaysService holidaysService;
	
	@GetMapping
	public List<HolidaysDto> getAll() {
		return holidaysMapper.holidaysToDtos(holidaysService.findAll());
	}

	@GetMapping("/{id}")
	public HolidaysDto getById(@PathVariable long id) {
		Holidays holiday = holidaysService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return holidaysMapper.holidayToDto(holiday);
	}
	
	@PostMapping
	public HolidaysDto addHoliday(@RequestBody @Valid HolidaysDto newHoliday) {
		Holidays holiday;
		try {
			holiday = holidaysService.addHoliday(holidaysMapper.dtoToHoliday(newHoliday));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidaysMapper.holidayToDto(holiday);
	}
}

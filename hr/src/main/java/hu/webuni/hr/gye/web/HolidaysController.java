package hu.webuni.hr.gye.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.dto.HolidaysDto;
import hu.webuni.hr.gye.exception.HolidayAlreadyApprovedException;
import hu.webuni.hr.gye.mapper.HolidaysMapper;
import hu.webuni.hr.gye.model.Employee;
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
	public List<HolidaysDto> getAll(Pageable page) {
		
		Page<Holidays> positionPage=holidaysService.findAll(page);
		List<HolidaysDto> listHoliday = holidaysMapper.holidaysToDtos(positionPage.getContent());

		return listHoliday;
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
	
	@PutMapping("/{id}")
	public HolidaysDto modifyHolidayRequest(@PathVariable long id, @RequestBody @Valid HolidaysDto modifiedHoliday) {
		
		Holidays holiday = null;
		
		try{
			holiday = holidaysService.modifyHoliday(id, holidaysMapper.dtoToHoliday(modifiedHoliday));
		} catch (HolidayAlreadyApprovedException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidaysMapper.holidayToDto(holiday);
	}

	@DeleteMapping("/{id}")
	public void deleteHolidayRequest(@PathVariable long id) {
		try{
			holidaysService.deleteHoliday(id);
		} catch (HolidayAlreadyApprovedException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}/approval", params = { "approve", "approverId" })
	public HolidaysDto approveHoliday(@PathVariable long id, @RequestParam long approverId, @RequestParam boolean approve) {
		Holidays holiday;
		try {
			holiday = holidaysService.approveHoliday(id, approverId, approve);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (HolidayAlreadyApprovedException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return holidaysMapper.holidayToDto(holiday);
	}

	@PostMapping("/holidaySearch")
	public List<HolidaysDto> getByParams(@RequestBody HolidaysDto holidayDto,Pageable page){

		Page<Holidays> positionPage = holidaysService.holidaySearch(holidaysMapper.dtoToHoliday(holidayDto),page);
		List<HolidaysDto> listHoliday = holidaysMapper.holidaysToDtos(positionPage.getContent());

		return listHoliday;
	}
}

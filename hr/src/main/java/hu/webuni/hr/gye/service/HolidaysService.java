package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.repository.HolidaysRepository;

@Service
public class HolidaysService {
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	HolidaysRepository holidaysRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Transactional
	public List<Holidays> findAll(){
		List<Holidays> hList = holidaysRepository.findAll();
		return hList;
	}
	
	@Transactional
	public Optional<Holidays> findById(Long id){
		Optional<Holidays> holiday = holidaysRepository.findById(id);
		return holiday;		
	}

	public Holidays addHoliday(Holidays holiday) {
		Long employeeId = holiday.getCreatedBy().getEmployeeID();
		Employee employee = employeeService.findByIdWithHoliday(employeeId);
		List<Holidays> holidaysList = employee.getHolidays();
		if(holidaysList==null) {
			holidaysList = new ArrayList<>();
		}
		holidaysList.add(holiday);
		employee.setHolidays(holidaysList);
		
		holiday.setCreatedDate(LocalDateTime.now());
		return holidaysRepository.save(holiday);
	}
	
	
}

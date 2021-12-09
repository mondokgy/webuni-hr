package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.gye.exception.HolidayAlreadyApprovedException;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.model.Holidays.HolidayStatus;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.HolidaysRepository;

@Service
public class HolidaysService {
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	HolidaysRepository holidaysRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Transactional
	public Page<Holidays> findAll(Pageable page){
		Page<Holidays> hList = holidaysRepository.findAll(page);
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
		holiday.setStatus(HolidayStatus.NEW);
		holiday.setStatusDate(LocalDateTime.now());
		holiday.setApprovedBy(null);
		return holidaysRepository.save(holiday);
	}

	@Transactional
	public Holidays modifyHoliday(long id, Holidays newHoliday) throws HolidayAlreadyApprovedException {
		Holidays holiday = holidaysRepository.findById(id).get();
		if (holiday.getApprovedBy() != null)
			throw new HolidayAlreadyApprovedException("Holiday is approved already.");
		if (holiday.getStatus().equals(HolidayStatus.DELETED))
			throw new HolidayAlreadyApprovedException("Holiday is deleted already.");
		newHoliday.setHolidaysId(id);
		newHoliday.setCreatedBy(newHoliday.getCreatedBy());
		newHoliday.setCreatedDate(LocalDateTime.now());
		newHoliday.setStatus(HolidayStatus.NEW);
		newHoliday.setStatusDate(LocalDateTime.now());
		newHoliday.setApprovedBy(null);
		
		
		holiday = holidaysRepository.save(newHoliday);
		return holiday;
	}

	@Transactional
	public void deleteHoliday(long id) throws HolidayAlreadyApprovedException {
		Holidays holiday = holidaysRepository.findById(id).get();
		if (holiday.getApprovedBy() != null)
			throw new HolidayAlreadyApprovedException("Holiday is approved already.");
		if (holiday.getStatus().equals(HolidayStatus.DELETED))
			throw new HolidayAlreadyApprovedException("Holiday is deleted already.");
		List<Holidays> holidayList = holiday.getCreatedBy().getHolidays();
		holidayList.remove(holiday);
		holiday.setStatus(HolidayStatus.DELETED);
		holiday.setStatusDate(LocalDateTime.now());
		holiday = holidaysRepository.save(holiday);
	}
	
	@Transactional
	public Holidays approveHoliday(long id, long approverId, boolean approve) throws HolidayAlreadyApprovedException {
		Holidays holiday = holidaysRepository.findById(id).get();
		if (holiday.getApprovedBy() != null)
			throw new HolidayAlreadyApprovedException("Holiday is approved already.");
		if (holiday.getStatus().equals(HolidayStatus.DELETED))
			throw new HolidayAlreadyApprovedException("Holiday is deleted already.");
		holiday.setApproveDate(LocalDateTime.now());
		holiday.setStatusDate(LocalDateTime.now());
		Employee approver = employeeRepository.findById(approverId)
				.orElseThrow(()->new NoSuchElementException());
		holiday.setApprovedBy(approver);
		if(approve) {
			holiday.setStatus(HolidayStatus.APPROVED);	
		}else {
			holiday.setStatus(HolidayStatus.REJECTED);	
		}
		return holiday;
	}

	public Page<Holidays> holidaySearch(Holidays holiday, Pageable page) {

		String status 				= holiday.getStatus().toString();
		Employee employee 			= holiday.getCreatedBy();
		Employee approver 			= holiday.getApprovedBy();
		LocalDateTime stratDate 	= holiday.getHolidayStart();
		LocalDateTime endDate 		= holiday.getHolidayEnd();
		LocalDateTime createddate 	= holiday.getCreatedDate();
		
		Specification <Holidays> spec = Specification.where(null);
		
		if (employee.getEmployeeID() != null)
			employee = employeeRepository.findById(employee.getEmployeeID()).get();
		if (approver.getEmployeeID() != null)
			approver  = employeeRepository.findById(approver.getEmployeeID()).get();
		
		if(StringUtils.hasText(status)) {
			spec = spec.and(HolidaySpecifications.hasStatus(status));
		}
		if(employee != null && StringUtils.hasText(employee.getName())) {
			spec = spec.and(HolidaySpecifications.hasEmployee(employee.getName()));
		}
		if(approver != null && StringUtils.hasText(approver.getName())) {
			spec = spec.and(HolidaySpecifications.hasApprover(approver.getName()));
		}
		if(createddate !=null) {
			spec = spec.and(HolidaySpecifications.hasCreatedDate(createddate));
		}
		if(stratDate != null && endDate != null) {
			spec = spec.and(HolidaySpecifications.hasHolidayDatesStartPeriod(stratDate));
			spec = spec.and(HolidaySpecifications.hasHolidayDatesEndPeriod(endDate));
		}

		return holidaysRepository.findAll(spec,page);
	}
	
}

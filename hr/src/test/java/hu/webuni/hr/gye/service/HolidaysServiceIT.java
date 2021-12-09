package hu.webuni.hr.gye.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.model.Holidays.HolidayStatus;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.HolidaysRepository;
import hu.webuni.hr.gye.repository.PositionDetailRepository;
import hu.webuni.hr.gye.repository.PositionRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class HolidaysServiceIT {


	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	PositionDetailRepository positionDetailRepository;
	
	@Autowired
	HolidaysRepository holidayRepository;
	
	@Autowired
	HolidaysService holidayService;
	
	@BeforeEach
	public void init() {
		holidayRepository.deleteAll();
		employeeRepository.deleteAll();
		positionDetailRepository.deleteAll();
		positionRepository.deleteAll();
	}
	
	List<Employee> initData() {
		
		Position p1 = positionRepository.save(new Position("programmer","foiskola"));
		Position p2 = positionRepository.save(new Position("architect","egyetem"));
		Position p3 = positionRepository.save(new Position("tester","erettsegi"));

		LocalDateTime dateTime = LocalDateTime.now();
		
		List<Employee> eList = new ArrayList<Employee>();
		
		Employee e1 = employeeRepository.save(new Employee("Test Employee1",p1,15000,dateTime));
		eList.add(e1);
		Employee e2 = employeeRepository.save(new Employee("Test Employee2",p2,5000,dateTime.plusDays(50)));
		eList.add(e2);
		Employee e3 = employeeRepository.save(new Employee("Test Employee3",p3,8000,dateTime.minusDays(50)));
		eList.add(e3);
		
		return eList;
	}
	
	@Test
	void testcreateHoliday() throws Exception{
		
		List<Employee> eList = initData();
		
		List<Holidays> allHolidaysBefore = holidayRepository.findAll();
		
		Employee employee = eList.get(0);
		
		LocalDateTime dateTime = LocalDateTime.now();
	
		Holidays holiday;
	
		for (int i = 0; i < 5; i++) {
			holiday = new Holidays(null, employee, dateTime, null, null, dateTime.plusDays(10), dateTime.plusDays(17), HolidayStatus.NEW, dateTime); 
			holidayService.addHoliday(holiday);
		}

		List<Holidays> allHolidaysAfter = holidayRepository.findAll();
		
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size() + 5);
		
	}
	
	@Test
	void testdeleteHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<Holidays> allHolidaysBefore = holidayRepository.findAll();

		Long testId = allHolidaysBefore.get(0).getHolidaysId();
		
		holidayService.deleteHoliday(testId);
		
		Holidays holiday = holidayRepository.findById(testId).get();
				
		List<Holidays> allHolidaysAfter = holidayRepository.findAll();
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.DELETED);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testmodifyHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<Holidays> allHolidaysBefore = holidayRepository.findAll();

		Long testId = allHolidaysBefore.get(0).getHolidaysId();
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		List<Employee> employeeList = employeeRepository.findAll();
		
		Employee employee = employeeList.get(employeeList.size()-1);
		
		LocalDateTime start = dateTime.plusDays(300);
		LocalDateTime end = dateTime.plusDays(310);
		
		Holidays newHoliday = new Holidays(null, employee, dateTime, null, null, start, end, HolidayStatus.NEW, dateTime);
		
		holidayService.modifyHoliday(testId, newHoliday);
		
		Holidays holiday = holidayRepository.findWithEmployeeById(testId);
				
		List<Holidays> allHolidaysAfter = holidayRepository.findAll();
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.NEW);
		assertThat(holiday.getCreatedBy().getEmployeeID()).isEqualTo(employee.getEmployeeID());
		assertThat(holiday.getHolidayStart()).isEqualToIgnoringSeconds(start);
		assertThat(holiday.getHolidayEnd()).isEqualToIgnoringSeconds(end);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testApproveHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<Holidays> allHolidaysBefore = holidayRepository.findAll();

		Long testId = allHolidaysBefore.get(0).getHolidaysId();

		List<Employee> employeeList = employeeRepository.findAll();
		
		Employee employee = employeeList.get(employeeList.size()-1);
		
		holidayService.approveHoliday(testId, employee.getEmployeeID(), true);
		
		Holidays holiday = holidayRepository.findWithEmployeeById(testId);
				
		List<Holidays> allHolidaysAfter = holidayRepository.findAll();
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.APPROVED);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testRejectHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<Holidays> allHolidaysBefore = holidayRepository.findAll();

		Long testId = allHolidaysBefore.get(0).getHolidaysId();

		List<Employee> employeeList = employeeRepository.findAll();
		
		Employee employee = employeeList.get(employeeList.size()-1);
		
		holidayService.approveHoliday(testId, employee.getEmployeeID(), false);
		
		Holidays holiday = holidayRepository.findWithEmployeeById(testId);
				
		List<Holidays> allHolidaysAfter = holidayRepository.findAll();
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.REJECTED);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
}

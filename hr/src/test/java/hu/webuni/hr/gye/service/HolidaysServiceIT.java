package hu.webuni.hr.gye.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.dto.HolidaysDto;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.model.Holidays.HolidayStatus;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.HolidaysRepository;
import hu.webuni.hr.gye.repository.PositionDetailRepository;
import hu.webuni.hr.gye.repository.PositionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HolidaysServiceIT {

	private static final String BASE_URI = "/api/holiday";
	
	@Autowired
	WebTestClient webTestClient;
	
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
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	String userName = "user1";
	String pass = "pass";
	String userManager = "user";
	
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
		
		Employee e1 = new Employee("Test Employee1",p1,15000,dateTime);
		e1.setUsername("user");
		e1.setPassword(passwordEncoder.encode("pass"));
		e1.setManager(e1);
		employeeRepository.save(e1);
		eList.add(e1);
		Employee e2 = new Employee("Test Employee2",p2,5000,dateTime.plusDays(50));
		e2.setUsername("user1");
		e2.setPassword(passwordEncoder.encode("pass"));
		e2.setManager(e1);
		employeeRepository.save(e2);
		eList.add(e2);		
		Employee e3 = new Employee("Test Employee3",p3,8000,dateTime.minusDays(50));
		e3.setUsername("user2");
		e3.setPassword(passwordEncoder.encode("pass"));
		e3.setManager(e1);
		employeeRepository.save(e3);
		eList.add(e3);
		
		return eList;
	}
	
	@Test
	void testcreateHoliday() throws Exception{
		
		List<Employee> eList = initData();
		
		List<HolidaysDto> allHolidaysBefore = getAllHolidays();
		
		Employee employee = eList.get(1);
		
		LocalDateTime dateTime = LocalDateTime.now();
	
		HolidaysDto holiday;
	
		for (int i = 0; i < 5; i++) {
			holiday  = new HolidaysDto(null, employee.getEmployeeID(), dateTime, null, null, dateTime.plusDays(10), dateTime.plusDays(17), HolidayStatus.NEW.toString(), dateTime); 			
			saveHolidays(holiday).expectStatus().isOk();
		}

		List<HolidaysDto> allHolidaysAfter = getAllHolidays();
		
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size() + 5);
		
	}
	
	@Test
	void testdeleteHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<HolidaysDto> allHolidaysBefore = getAllHolidays();
		
		Long testId = allHolidaysBefore.get(0).getHolidaysId();
		
		deleteHolidays(testId).expectStatus().isOk();
		
		Holidays holiday = holidayRepository.findById(testId).get();
				
		List<HolidaysDto> allHolidaysAfter = getAllHolidays();
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.DELETED);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testmodifyHoliday() throws Exception{
		
		testcreateHoliday();
		
		List<HolidaysDto> allHolidaysBefore = getAllHolidays();
		
		HolidaysDto  testHoliday = allHolidaysBefore.get(0);
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		LocalDateTime start = dateTime.plusDays(300);
		LocalDateTime end = dateTime.plusDays(310);
		
		testHoliday.setHolidayStart(start);
		testHoliday.setHolidayEnd(end);
		
		modifyHolidays(testHoliday).expectStatus().isOk();
		
		List<HolidaysDto> allHolidaysAfter = getAllHolidays();
		
		HolidaysDto holiday = allHolidaysAfter.get(0);
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.NEW.toString());
		assertThat(holiday.getEmployeeId()).isEqualTo(testHoliday.getEmployeeId());
		assertThat(holiday.getHolidayStart()).isEqualToIgnoringSeconds(start);
		assertThat(holiday.getHolidayEnd()).isEqualToIgnoringSeconds(end);
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testApproveHoliday() throws Exception{
		
		testcreateHoliday();

		List<HolidaysDto> allHolidaysBefore = getAllHolidays();
		
		HolidaysDto testHoliday = allHolidaysBefore.get(0);

		Employee employee = employeeRepository.findById(testHoliday.getEmployeeId()).get();

		Long managerId = employee.getManager().getEmployeeID();
		
		approveyHolidays(testHoliday.getHolidaysId(), managerId, true).expectStatus().isOk();

		List<HolidaysDto> allHolidaysAfter = getAllHolidays();
		HolidaysDto holiday = allHolidaysAfter.get(0);
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.APPROVED.toString());
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	@Test
	void testRejectHoliday() throws Exception{
		
		testcreateHoliday();

		List<HolidaysDto> allHolidaysBefore = getAllHolidays();
		
		HolidaysDto testHoliday = allHolidaysBefore.get(0);

		Employee employee = employeeRepository.findById(testHoliday.getEmployeeId()).get();

		Long managerId = employee.getManager().getEmployeeID();
		
		approveyHolidays(testHoliday.getHolidaysId(), managerId, false).expectStatus().isOk();

		List<HolidaysDto> allHolidaysAfter = getAllHolidays();
		HolidaysDto holiday = allHolidaysAfter.get(0);
		
		assertThat(holiday.getStatus()).isEqualTo(HolidayStatus.REJECTED.toString());
		assertThat(allHolidaysAfter.size()).isEqualTo(allHolidaysBefore.size());
	}
	
	
	private ResponseSpec modifyHolidays(HolidaysDto newHoliday) {
		String path = BASE_URI + "/" + newHoliday.getHolidaysId();
		return webTestClient
				.put()
				.uri(path)
				.headers(headers -> headers.setBasicAuth(userName, pass))
				.bodyValue(newHoliday)
				.exchange();
	}
	
	private ResponseSpec deleteHolidays(Long testId) {
		String path = BASE_URI + "/" + testId.toString();
		return webTestClient
				.delete()
				.uri(path)
				.headers(headers -> headers.setBasicAuth(userName, pass))
				.exchange();
	}
	
	private ResponseSpec saveHolidays(HolidaysDto newHoliday) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth(userName, pass))
				.bodyValue(newHoliday)
				.exchange();
	}

	private List<HolidaysDto> getAllHolidays() {
		List<HolidaysDto> responseList = webTestClient
				.get()
				.uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth(userName, pass))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(HolidaysDto.class)
				.returnResult()
				.getResponseBody();
		Collections.sort(responseList, Comparator.comparing(HolidaysDto::getHolidaysId));
		return responseList;
	}
	
	private ResponseSpec approveyHolidays(Long testId, Long approveId, Boolean approve) {
		String path = BASE_URI + "/" + testId +"/approval?approverId="+approveId+"&approve="+approve.toString();
		return webTestClient
				.put()
				.uri(path)
				.headers(headers -> headers.setBasicAuth(userManager, pass))
				.exchange();
	}
}

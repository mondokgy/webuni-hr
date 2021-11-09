package hu.webuni.hr.gye.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.gye.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

	private static final String BASE_URI = "/api/employees";
	
	@Autowired
	WebTestClient webTestClient;
	@Test
	void testThatValidEmployeeCreated() throws Exception{
		List<EmployeeDto> allEmployeeBefore = getAllEmployees();
		
		EmployeeDto newEmployee = getTestEmployeeDto(true,"");
		
		createEmployee(newEmployee)
			.expectStatus().isOk();
		List<EmployeeDto> allEmployeeAfter = getAllEmployees();
		
		assertThat(allEmployeeAfter.subList(0, allEmployeeBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(allEmployeeBefore);
	
		assertThat(allEmployeeAfter.get(allEmployeeAfter.size()-1))
		.usingRecursiveComparison()
		.ignoringFields("employeeID")
		.isEqualTo(newEmployee);
	}

	@Test
	void testThatEmployeeCreatedWithInvalidName() throws Exception{
		createEmployee(getTestEmployeeDto(false,"invalid_name"))
			.expectStatus().isBadRequest();
	}
	
	@Test
	void testThatEmployeeCreatedWithInvalidPosition() throws Exception{
		createEmployee(getTestEmployeeDto(false,"invalid_position"))
			.expectStatus().isBadRequest();
	}
	
	@Test
	void testThatEmployeeCreatedWithInvalidSalary() throws Exception{
		createEmployee(getTestEmployeeDto(false,"invalid_salary"))
			.expectStatus().isBadRequest();
	}
	
	@Test
	void testThatEmployeeCreatedWithInvalidStartDate() throws Exception{
		createEmployee(getTestEmployeeDto(false,"invalid_startDate"))
			.expectStatus().isBadRequest();
	}
	
	@Test
	void testThatEmployeeModified() throws Exception{
		EmployeeDto employee = createEmployee(getTestEmployeeDto(true,""))
			.expectStatus().isOk()
			.expectBody(EmployeeDto.class).returnResult().getResponseBody();
		
		EmployeeDto modifiedEmployee = changeEmployee(employee);
		
		modifyEmployee(modifiedEmployee)
			.expectStatus().isOk();
		
		List<EmployeeDto> employeeAfter = getAllEmployees();
		
		assertThat(employeeAfter.get(employeeAfter.size()-1))
			.usingRecursiveComparison()
			.isEqualTo(modifiedEmployee);
			
	}
	
	private ResponseSpec createEmployee(EmployeeDto newEmployee) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(newEmployee)
				.exchange();
	}
	
	private ResponseSpec modifyEmployee(EmployeeDto employee) {
		return webTestClient
				.put()
				.uri(BASE_URI + "/" + employee.getEmployeeID())
				.bodyValue(employee)
				.exchange();
	}
	
	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> responseList = webTestClient
				.get()
				.uri(BASE_URI)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(EmployeeDto.class)
				.returnResult()
				.getResponseBody();
		
		Collections.sort(responseList, Comparator.comparing(EmployeeDto::getEmployeeID));
		
		return responseList;
	}
	
	
	private EmployeeDto getTestEmployeeDto(boolean isValid, String invalidType) {
		EmployeeDto employeeDto;
		
		if(isValid) {
			employeeDto = new EmployeeDto(null,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40));
		}else {
		 switch(invalidType)
	        {
	            case "invalid_salary":
	            	employeeDto = new EmployeeDto(3L,"Teszt Elek", "tester", -1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40));
	                break;
	            case "invalid_startDate":
	            	employeeDto = new EmployeeDto(3L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2031,Month.JANUARY, 15, 19, 30, 40));
	                break;
	            case "invalid_name":
	            	employeeDto = new EmployeeDto(3L,"", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40));
	                break;
	            case "invalid_position":
	            	employeeDto = new EmployeeDto(3L,"Teszt Elek", "", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40));
	                break;
	            default:
	            	employeeDto = new EmployeeDto(3L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40));
	        }
		}
		
		return employeeDto;
	}
	
	private EmployeeDto changeEmployee(EmployeeDto employee) {
		employee.setPosition("New_position");
		employee.setName("New_name");
		employee.setSalary(5000);
		return employee;
	}
}

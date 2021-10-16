package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.gye.dto.EmployeeDto;

@Controller
public class HrTlController {
	
	private List<EmployeeDto> employeeList = new ArrayList<>();
	
	{
		employeeList.add(new EmployeeDto(1L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));
		employeeList.add(new EmployeeDto(2L,"Próba Róza", "tester", 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String,Object> model) {
		
		model.put("employees", employeeList);
		model.put("newEmployee", new EmployeeDto());
		return "employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(EmployeeDto employee) {
		LocalDateTime now = LocalDateTime.now();
		employee.setStartWork(now);
		employeeList.add(employee);
		return"redirect:employees";
	}
}

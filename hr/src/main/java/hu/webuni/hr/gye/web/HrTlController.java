package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.webuni.hr.gye.config.HrConfigProperties;
import hu.webuni.hr.gye.dto.EmployeeDto;

@Controller
public class HrTlController {
	
	@Autowired
	HrConfigProperties config;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	private TreeMap<Long, EmployeeDto> employeeListMap = new TreeMap<Long, EmployeeDto>();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime tempDate = LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40);
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime tempDate2 = LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40);
	
	{
		
		employeeListMap.put(1L,new EmployeeDto(1L,"Teszt Elek", "tester", 1000, tempDate));		
		employeeListMap.put(2L,new EmployeeDto(2L,"Próba Róza", "tester", 3000,tempDate2));

	}
	
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String,Object> model) {
		
		List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>(employeeListMap.values());
		
		EmployeeDto newEmployee = new EmployeeDto();
		newEmployee.setEmployeeID(employeeListMap.lastKey()+1);
		
		List<String> positions = config.getPosition();
		
		positions.forEach(s -> log.debug(s));
		
		model.put("employees", employeeList);
		model.put("newEmployee", newEmployee);
		model.put("positions", positions);
		
		return "employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(EmployeeDto employee) {
		
		log.debug("/employees PostMapping start");
		LocalDateTime oldStartDate = null;
		
		EmployeeDto currentEmployee = employeeListMap.get(employee.getEmployeeID());
		
		if(currentEmployee != null){
				log.debug("/employees PostMapping update");
				//employee.setStartWork(currentEmployee.getStartWork());
				employeeListMap.replace(employee.getEmployeeID(),employee);		
		}else {
			log.debug("/employees PostMapping insert");

			LocalDateTime now = LocalDateTime.now();
			employee.setStartWork(now);
			
			employeeListMap.put(employee.getEmployeeID(), employee);
		}
				
		log.debug("/employees PostMapping end");
		return"redirect:employees";
	}
	
	@PostMapping("/employeeDelete")
	public String deleteEmployee(@RequestParam(value = "employeeId", required = false) Long id) {
		
		log.debug("/employees DeleteMapping start");
		employeeListMap.remove(id);
		log.debug("/employees DeleteMapping end");
		return"redirect:employees";
	}
	
	@GetMapping("/employee")
	public String viewEmployee(@RequestParam(value = "employeeId", required = false) Long id, Map<String,Object> model) {
		
		EmployeeDto employee=new EmployeeDto();
		
		employee = employeeListMap.get(id);
		
		List<String> positions = config.getPosition();	
		positions.forEach(s -> log.debug(s));
		
		model.put("employee", employee);
		model.put("positions", positions);
		
		return "employee";
	}
}

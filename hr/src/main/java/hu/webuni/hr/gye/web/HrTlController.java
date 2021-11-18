package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.webuni.hr.gye.config.HrConfigProperties;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.repository.PositionRepository;

@Controller
public class HrTlController {
	
	@Autowired
	HrConfigProperties config;
	
	@Autowired
	PositionRepository positionRepository;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	private TreeMap<Long, Employee> employeeListMap = new TreeMap<Long, Employee>();
	//tesztadat feltöltés
//	{
//		
//		employeeListMap.put(1L,new Employee(1L,"Teszt Elek", positionRepository.findByName("tester"), 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));		
//		employeeListMap.put(2L,new Employee(2L,"Próba Róza", positionRepository.findByName("tester"), 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
//
//	}
	
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String,Object> model) {
		log.info("thymeleaf controller, /employees, get, listEmployees start");
		List<Employee> employeeList = new ArrayList<Employee>(employeeListMap.values());
		
		Employee newEmployee = new Employee();
		newEmployee.setEmployeeID(employeeListMap.lastKey()+1);
		
		List<String> positions = config.getPosition();
		
		log.debug("positions:");
		positions.forEach(s -> log.debug(s));
		log.debug("employees:");
		employeeList.forEach(e -> log.debug(e.toString()));
		
		model.put("employees", employeeList);
		model.put("newEmployee", newEmployee);
		model.put("positions", positions);
		
		log.info("thymeleaf controller, /employees, get, listEmployees end");
		
		return "employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		
		log.info("thymeleaf controller, /employees, post, addEmployee start");
		LocalDateTime oldStartDate = null;
		
		Employee currentEmployee = employeeListMap.get(employee.getEmployeeID());
		
		if(currentEmployee != null){
				log.debug("Employee exists,  update: " + employee.toString());
			
				employeeListMap.replace(employee.getEmployeeID(),employee);		
		}else {
			LocalDateTime now = LocalDateTime.now();
			employee.setStartWork(now);
			
			log.debug("Employee not exists,  insert: " + employee.toString());

			employeeListMap.put(employee.getEmployeeID(), employee);
		}
				
		log.info("thymeleaf controller, /employees, post, addEmployee end");
		return"redirect:employees";
	}
	
	@PostMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam(value = "employeeId", required = false) Long id) {
		
		log.info("thymeleaf controller, /employeeDelete, post, deleteEmployee start");
		log.debug("deleted employee: " + employeeListMap.get(id).toString());
		employeeListMap.remove(id);
		log.info("thymeleaf controller, /employeeDelete, post, deleteEmployee end");
		return"redirect:employees";
	}
	
	@GetMapping("/employee")
	public String viewEmployee(@RequestParam(value = "employeeId", required = false) Long id, Map<String,Object> model) {
		log.info("thymeleaf controller, /employee, get, viewEmployee start");
		Employee employee=new Employee();
		
		employee = employeeListMap.get(id);
		
		log.debug("employee: " + employee.toString());
		
		log.debug("positions:");
		List<String> positions = config.getPosition();	
		positions.forEach(s -> log.debug(s));
		
		model.put("employee", employee);
		model.put("positions", positions);
		
		log.info("thymeleaf controller, /employee, get, viewEmployee start");
		return "employee";
	}
}

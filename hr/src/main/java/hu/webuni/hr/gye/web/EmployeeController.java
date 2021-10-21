package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gye.dto.EmployeeDto;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	private Map<Long, EmployeeDto> employees = new HashMap<>();
	
	{		
		employees.put(1L,new EmployeeDto(1L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));		
		employees.put(2L,new EmployeeDto(2L,"Próba Róza", "tester", 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
	}
	
	@GetMapping
	public List<EmployeeDto> getAll(@RequestParam(required = false) Long salary){
		
		log.debug("restapi controller, /, get, getAll start");
		
		if(salary == null) {
			log.debug("Salary null, return all employee.");
			log.debug("restapi controller, /, get, getAll end");
			
			return new ArrayList<>(employees.values());
		}else {
			log.debug("salary not null:*"+salary+"*, return filtered employees");
			
			List<EmployeeDto> candidateEmployee = new ArrayList<>(employees.values());

			for(EmployeeDto employee : candidateEmployee) {
				log.debug("ciklus, id:*"+employee.getEmployeeID()+"*");
				if(employee.getSalary()<=salary) {
					log.debug("Filtered by Salary.");
					candidateEmployee.remove(employee);
				}
			}
			
			log.debug("restapi controller, /, get, getAll end");
			return candidateEmployee;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable Long id){
		
		log.debug("restapi controller, /{id}, get, getById start");
		
		EmployeeDto employeeDto = employees.get(id);
		
		if(employeeDto != null) {
			log.debug("restapi controller, /{id}, get, getById end");
			return ResponseEntity.ok(employeeDto);
		}else {
			log.debug("restapi controller, /{id}, get, getById end");
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /, post, createEmployee start");
		
		employees.put(employeeDto.getEmployeeID(), employeeDto);
		
		log.debug("restapi controller, /, post, createEmployee end");
		
		return employeeDto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /{id}, put, modifyEmployee start");
		
		if(!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		employeeDto.setEmployeeID(id);
		employees.put(id, employeeDto);
		
		log.debug("restapi controller, /{id}, put, modifyEmployee end");
		
		return ResponseEntity.ok(employeeDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable Long id) {
		
		log.debug("restapi controller, /{id}, delete, deleteEmployee start");
		
		EmployeeDto employeeDto = employees.get(id);
		
		if(employeeDto != null) {
			employees.remove(id);
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return ResponseEntity.ok(employeeDto);
		}else {
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return ResponseEntity.notFound().build();
		}	
	}
	

}

package hu.webuni.hr.gye.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import hu.webuni.hr.gye.mapper.EmployeeMapper;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@GetMapping
	public List<EmployeeDto> getAll(@RequestParam(required = false) Long salary){
		
		log.debug("restapi controller, /, get, getAll start");
		
		if(salary == null) {
			log.debug("Salary null, return all employee.");
			log.debug("restapi controller, /, get, getAll end");
			
			return employeeMapper.employeesToDto(employeeService.findAll());
			
		}else {
			log.debug("salary not null:*"+salary+"*, return filtered employees");
			
			List<EmployeeDto> candidateEmployee = new ArrayList<>();
			List<EmployeeDto> allEmployee = employeeMapper.employeesToDto(employeeService.findAll());
			
			for(EmployeeDto employee : allEmployee) {
				log.debug("ciklus, id:*"+employee.getEmployeeID()+"*");
				if(employee.getSalary()>salary) {
					log.debug("add to list.");
					candidateEmployee.add(employee);
				}
			}
			
			log.debug("restapi controller, /, get, getAll end");
			return candidateEmployee;
		}
	}
	
	@GetMapping("/{id}")
	public EmployeeDto getById(@PathVariable Long id){
		
		log.debug("restapi controller, /{id}, get, getById start");
		
		EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.findById(id));
		
		if(employeeDto != null) {
			log.debug("restapi controller, /{id}, get, getById end");
			return employeeDto;
		}else {
			log.debug("Invalid input: employeeDto.");
			log.debug("restapi controller, /{id}, get, getById end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /, post, createEmployee start");		
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		log.debug("restapi controller, /, post, createEmployee end");
		
		return  employeeMapper.employeeToDto(employee);
	}
	
	@PutMapping("/{id}")
	public EmployeeDto modifyEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /{id}, put, modifyEmployee start");

		Employee employee = employeeService.findById(id);

		if(employee != null) {
			employee = employeeService.modify(id, employeeMapper.dtoToEmployee(employeeDto));
			log.debug("restapi controller, /{id}, put, modifyEmployee end");		
			return employeeMapper.employeeToDto(employee);
		}else {
			log.debug("Invalid input: employeeDto.");
			log.debug("restapi controller, /{id}, put, modifyEmployee end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}	
	}
	
	@DeleteMapping("/{id}")
	public EmployeeDto deleteEmployee(@PathVariable Long id) {
		
		log.debug("restapi controller, /{id}, delete, deleteEmployee start");
		
		EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.findById(id));
		
		if(employeeDto != null) {
			
			Employee employee = employeeService.delete(id);
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return employeeMapper.employeeToDto(employee);
		}else {
			log.debug("Invalid input: employeeDto.");
			log.debug("restapi controller, /{id}, get, getById end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}	
	}
	

}

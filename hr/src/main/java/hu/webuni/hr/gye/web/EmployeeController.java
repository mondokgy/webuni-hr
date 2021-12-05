package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.exception.TooManyRequestParamsException;
import hu.webuni.hr.gye.mapper.EmployeeMapper;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;
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
	public List<EmployeeDto> getAll(@RequestParam(required = false) String salary, 
									@RequestParam(required = false) String namePrefix,
									@RequestParam(required = false) String startFrom,
									@RequestParam(required = false) String startTo){
		
		log.debug("restapi controller, /, get, getAll start");
		
		Map<String,String> reqParams = new HashMap<String,String>();
		
		String params = "";
		
		if(salary != null) {
			params = params + "salary:"+salary+";";
			log.debug("restapi controller, /, get, getAll start");
			reqParams.put("salary", salary);
		}
		if(namePrefix != null) {
			params = params + "namePrefix:"+namePrefix+";";
			reqParams.put("namePrefix", namePrefix);
		}		
		if(startFrom != null) {
			params = params + "startFrom:"+startFrom+";";
			if(startTo != null) {	
				reqParams.put("startDate", startFrom + "_" + startTo);
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}		
		if(startTo != null) {
			params = params + "startTo:"+startTo+";";
			if(startFrom == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
		
		log.debug("restapi controller, /, get, getAll params: " + params);
		

		try {
			List<EmployeeDto> listEmployee = new ArrayList<EmployeeDto>();
			
			listEmployee = employeeMapper.employeesToDto(employeeService.findBy(reqParams));
			
			log.debug("restapi controller, /, get, getAll end");
			return listEmployee;
		}catch(TooManyRequestParamsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(params = "position")
	public List<EmployeeDto> getAll(@RequestParam String position, Pageable page) {
		log.debug("restapi controller, /, get, getAll params: position");

		List<EmployeeDto> listEmployee = new ArrayList<EmployeeDto>();
		
		Page<Employee> positionPage=employeeService.findByPosition(position,page);
		log.debug("page, getNumber: "+positionPage.getNumber()+"");
		log.debug("page, isLast: "+positionPage.isLast()+"");
		log.debug("page, isFirst: "+positionPage.isFirst()+"");
		log.debug("page, getSize: "+positionPage.getSize()+"");
		log.debug("page, getTotalElements: "+positionPage.getTotalElements()+"");
		log.debug("page, getTotalPages: "+positionPage.getTotalPages()+"");
		listEmployee = employeeMapper.employeesToDto(positionPage.getContent());
			
		log.debug("restapi controller, /, get, getAll end");
		return listEmployee;
		
	}
	
	@GetMapping("/{id}")
	public EmployeeDto getById(@PathVariable Long id){
		
		log.debug("restapi controller, /{id}, get, getById start");
		
		Employee employee = employeeService.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		log.debug("restapi controller, /{id}, get, getById end");
		return employeeMapper.employeeToDto(employee);
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /, post, createEmployee start");		
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		log.debug("restapi controller, /, post, createEmployee end");
		
		return  employeeMapper.employeeToDto(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /{id}, put, modifyEmployee start");
		try {
			employeeDto = employeeMapper.employeeToDto(employeeService.modify(id, employeeMapper.dtoToEmployee(employeeDto)));
			log.debug("restapi controller, /{id}, put, modifyEmployee end");		
			return ResponseEntity.ok(employeeDto);
		}catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable Long id) {
		
		log.debug("restapi controller, /{id}, delete, deleteEmployee start");
		try {
			EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.delete(id));
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return ResponseEntity.ok(employeeDto);
		}catch (NoSuchElementException e) {
			log.debug("Invalid input: employeeDto.");
			log.debug("restapi controller, /{id}, get, getById end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
	}
	
	@GetMapping("/payraise")
	public int getPayRaise(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
	
	@PostMapping("/employeeSearch")
	public List<EmployeeDto> getByParams(@RequestBody EmployeeDto employeeDto){
		
		log.debug("restapi controller, /paramSearch, get, getByParams start");
		
		List<Employee> employees = employeeService.findEmployeeByExample(employeeMapper.dtoToEmployee(employeeDto));

		return employeeMapper.employeesToDto(employees);
	}
}

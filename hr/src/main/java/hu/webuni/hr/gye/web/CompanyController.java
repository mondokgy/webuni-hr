package hu.webuni.hr.gye.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import hu.webuni.hr.gye.mapper.CompanyMapper;
import hu.webuni.hr.gye.mapper.EmployeeMapper;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.service.CompanyService;
import hu.webuni.hr.gye.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");

	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full){
		
		log.debug("restapi controller, /, get, getAll start");
		List<Company> allCompanies = companyService.findAll();
		
		if(full == null || !full) {
			log.debug("Full is null or false, return without employee.");
			
			log.debug("restapi controller, /, get, getAll end");
			return companyMapper.companiesToDtoWithOutEmployee(allCompanies);
		}else {
			log.debug("Full is true, return filtered companies");
			log.debug("restapi controller, /, get, getAll end");
			
			return companyMapper.companiesToDto(allCompanies);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getById(@PathVariable Long id, @RequestParam(required = false) Boolean full){
		
		log.debug("restapi controller, /{id}, get, getById start");
		
		Company company = companyService.findById(id);
		
		if(company != null) {
			log.debug("restapi controller, /{id}, get, getById end");
			if(full == null || !full) {
				return ResponseEntity.ok(companyMapper.companyToDtoWithOutEmployee(company));
			}else {
				return ResponseEntity.ok(companyMapper.companyToDto(company));
			}
			
		}else {
			log.debug("restapi controller, /{id}, get, getById end");
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto companydto) {
		
		log.debug("restapi controller, /, post, createCompany start");
		
		Company company = companyService.save(companyMapper.dtoToCompany(companydto));
		
		log.debug("restapi controller, /, post, createCompany end");
		
		return companyMapper.companyToDto(company);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyEmployee(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
		
		log.debug("restapi controller, /{id}, put, modifyCompany start");

		try {
			companyDto = companyMapper.companyToDto(companyService.modify(id, companyMapper.dtoToCompany(companyDto)));
			log.debug("restapi controller, /{id}, put, modifyEmployee end");		
			return ResponseEntity.ok(companyDto);
		}catch (NoSuchElementException e) {
			log.debug("restapi controller, /{id}, put, Not_Found, modifyCompany end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
		
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable Long id) {
		
		log.debug("restapi controller, /{id}, delete, deleteCompany start");

		try {
			Company company = companyService.delete(id);
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}catch (NoSuchElementException e) {
			log.debug("restapi controller, /{id}, delete, deleteCompany end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}		
		
	}
	
	@PostMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /{id}/employees, post, addEmployeeToCompany start");
		
		Company company = companyService.findById(id);
		if(company != null) {
			List<Employee> employeeList = company.getEmployees();
			employeeList.add(employeeMapper.dtoToEmployee(employeeDto));
			company.setEmployees(employeeList);
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}else {
			log.debug("restapi controller, /{id}/employees, post, addEmployeeToCompany end");
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}/employees/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable Long id, @PathVariable Long employeeId) {
		
		log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany start");
		
		Company company = companyService.findById(id);
		
		if(company != null) {
			List<Employee> employeeList = company.getEmployees();
			employeeList.removeIf(employee -> employee.getEmployeeID().equals(employeeId));
			company.setEmployees(employeeList);
			log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany end");
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}else {
			log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany end");
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PutMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> replaceEmployeesOfCompany(@PathVariable Long id, @RequestBody List<EmployeeDto> listEmployees) {
		
		log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany start");
		
		Company company = companyService.findById(id);
		
		if(company != null) {
			
			company.setEmployees(employeeMapper.dtoToEmployees(listEmployees));
			
			log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany end");
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}else {
			log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany end");
			return ResponseEntity.notFound().build();
		}
		

	}
	

}

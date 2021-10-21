package hu.webuni.hr.gye.web;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	private EmployeeService employeeService;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	private Map<Long, CompanyDto> companies = new HashMap<>();
	
	{		
		Address address1 = new Address(1L,"Budapest","1111","Kossuth utca","10","telephely");
		Address address2 = new Address(2L,"Szolnok","5000", "Petőfi utca", "5", "levelezés");
		
		List<Address> listAddress = new ArrayList<Address>();
		List<EmployeeDto> listEmployees = new ArrayList<EmployeeDto>();
		
		listAddress.add(address1);
		listAddress.add(address2);
		
		listEmployees.add(new EmployeeDto(1L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));		
		listEmployees.add(new EmployeeDto(2L,"Próba Róza", "tester", 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
		
		List<EmployeeDto> listEmployees2 = new ArrayList<EmployeeDto>(listEmployees);
		
		companies.put(1L,new CompanyDto(1L,"Kódoló Kft.", "123456-t", listEmployees, listAddress));		
		companies.put(2L,new CompanyDto(2L,"MindenIT Bt. ", "976578-a", listEmployees2, listAddress));
	}
	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full){
		
		log.debug("restapi controller, /, get, getAll start");
		
		if(full == null || !full) {
			log.debug("Full is null or false, return without employee.");
			
			List<CompanyDto> allCompanies = new ArrayList<>(companies.values());
			List<CompanyDto> companiesWithOutEmployee = new ArrayList<>();
			
			for(CompanyDto company : allCompanies) {
				log.debug("ciklus, id:*"+company.getCompanyId()+"*");
				
				CompanyDto newCompanyDto = fileterEmployees(company);
				
				companiesWithOutEmployee.add(newCompanyDto);
			}
			
			log.debug("restapi controller, /, get, getAll end");
			return companiesWithOutEmployee;
		}else {
			log.debug("Full is true, return filtered companies");
			log.debug("restapi controller, /, get, getAll end");
			
			return new ArrayList<>(companies.values());
		}
	}

	private CompanyDto fileterEmployees(CompanyDto company) {
		CompanyDto newCompanyDto = new CompanyDto(company);
		newCompanyDto.setEmployees(null);
		return newCompanyDto;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getById(@PathVariable Long id, @RequestParam(required = false) Boolean full){
		
		log.debug("restapi controller, /{id}, get, getById start");
		
		CompanyDto companyDto = companies.get(id);
		
		if(companyDto != null) {
			log.debug("restapi controller, /{id}, get, getById end");
			if(full == null || !full) {
				return ResponseEntity.ok(fileterEmployees(companyDto));
			}else {
				return ResponseEntity.ok(companyDto);
			}
			
		}else {
			log.debug("restapi controller, /{id}, get, getById end");
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto companydto) {
		
		log.debug("restapi controller, /, post, createCompany start");
		
		companies.put(companydto.getCompanyId(), companydto);
		
		log.debug("restapi controller, /, post, createCompany end");
		
		return companydto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyEmployee(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
		
		log.debug("restapi controller, /{id}, put, modifyCompany start");
		
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		companyDto.setCompanyId(id);
		companies.put(id, companyDto);
		
		log.debug("restapi controller, /{id}, put, modifyCompany end");
		
		return ResponseEntity.ok(companyDto);
	}
	
		
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable Long id) {
		
		log.debug("restapi controller, /{id}, delete, deleteCompany start");
		
		CompanyDto companyDto = companies.get(id);
		
		if(companyDto != null) {
			companies.remove(id);
			log.debug("restapi controller, /{id}, delete, deleteEmployee end");
			return ResponseEntity.ok(companyDto);
		}else {
			log.debug("restapi controller, /{id}, delete, deleteCompany end");
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PostMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		
		log.debug("restapi controller, /{id}/employees, post, addEmployeeToCompany start");
		
		CompanyDto companyDto = companies.get(id);
		if(companyDto != null) {
			List<EmployeeDto> employeeList = companyDto.getEmployees();
			employeeList.add(employeeDto);
			companyDto.setEmployees(employeeList);
			return ResponseEntity.ok(companyDto);
		}else {
			log.debug("restapi controller, /{id}/employees, post, addEmployeeToCompany end");
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}/employees/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable Long id, @PathVariable Long employeeId) {
		
		log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany start");
		
		CompanyDto companyDto = companies.get(id);
		
		if(companyDto != null) {
			List<EmployeeDto> employeeList = companyDto.getEmployees();
			employeeList.removeIf(employee -> employee.getEmployeeID().equals(employeeId));
			log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany end");
			return ResponseEntity.ok(companyDto);
		}else {
			log.debug("restapi controller, /{id}, delete, deleteEmployeeFromCompany end");
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PutMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> replaceEmployeesOfCompany(@PathVariable Long id, @RequestBody List<EmployeeDto> listEmployees) {
		
		log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany start");
		
		CompanyDto companyDto = companies.get(id);
		
		if(companyDto != null) {
			
			companyDto.setEmployees(listEmployees);
			
			log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany end");
			return ResponseEntity.ok(companyDto);
		}else {
			log.debug("restapi controller, /{id}, put, replaceEmployeesOfCompany end");
			return ResponseEntity.notFound().build();
		}
		

	}
	
	@GetMapping("/payraise")
	public int getPayRaise(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
}

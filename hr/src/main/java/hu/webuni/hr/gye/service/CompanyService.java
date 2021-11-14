package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.repository.CompanyRepository;

@Service
public class CompanyService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	CompanyRepository companyRepository;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	public List<Company> findAll(){
		return  companyRepository.findAll();
	}
	
	public Optional<Company> findById(Long id){
		return companyRepository.findById(id);
	}
		
	public void delete(Long id) {
		
		log.debug("called CompanyService.delete()");
		findById(id).orElseThrow(()->new NoSuchElementException());
		companyRepository.deleteById(id);
		
	}
	
	public Company modify(Long id, Company changedCompany) {
		log.debug("called CompanyService.modify()");
		
		findById(id).orElseThrow(()->new NoSuchElementException());

		changedCompany.setCompanyId(id);
		
		return companyRepository.save(changedCompany);
	}
		
	
	public Company addEmployee(Long id, Employee employee) {
		log.debug("called CompanyService.modify()");		
		
		Company company =  findById(id).orElseThrow(()->new NoSuchElementException());

		List<Employee> employeeList = company.getEmployees();
		employeeList.add(employee);
		company.setEmployees(employeeList);
		
		return companyRepository.save(company);
	}
	
	public Company deleteEmployee(Long id, Long employeeId) {
		log.debug("called CompanyService.modify()");		
		
		Company company =  findById(id).orElseThrow(()->new NoSuchElementException());

		List<Employee> employeeList = company.getEmployees();
		employeeList.removeIf(employee -> employee.getEmployeeID().equals(employeeId));
		company.setEmployees(employeeList);
		
		return companyRepository.save(company);
	}
	
	public Company modifyEmployee(Long id, List<Employee> listEmployees) {
		log.debug("called CompanyService.modify()");		
		
		Company company =  findById(id).orElseThrow(()->new NoSuchElementException());

		company.setEmployees(listEmployees);
		
		return companyRepository.save(company);
	}
}

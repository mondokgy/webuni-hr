package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.repository.AddressRepository;
import hu.webuni.hr.gye.repository.CompanyRepository;
import hu.webuni.hr.gye.repository.EmployeeRepository;

@Service
public class CompanyService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	public Company save(Company company) {
		
		Company newCompany = companyRepository.save(company);
		
		company.getAddresses().forEach(a -> 
		{	a.setCompany(newCompany);
			addressRepository.save(a);
		});
		
		if(company.getEmployees() != null) {
			company.getEmployees().forEach(e -> 
			{	e.setCompany(newCompany);
				employeeRepository.save(e);
			});
		}
		
		return newCompany;
	}
	
	@Transactional
	public List<Company> findAll(){
		List<Company> clist = companyRepository.findAllWithEmployee();
		return companyRepository.findAllWithAddresses(clist);
	}
	
	@Transactional
	public Optional<Company> findById(Long id){
		Company company = companyRepository.findWithEmployeeById(id);
		Optional<Company> optCompany = Optional.of(companyRepository.findWithAddressesById(company));
		return optCompany;		
	}
		
	@Transactional
	public List<Company> findByEmployeeWithSalaryHigherThan(int salary){
		List<Company> companyList = companyRepository.findByEmployeeWithSalaryHigherThan(salary);
		return companyRepository.findAllWithAddresses(companyList);
	}
	
	@Transactional
	public List<Company> findByEmployeeCountHigherThan(int count){
		List<Company> companyList = companyRepository.findByEmployeeCountHigherThan(count);
		return companyRepository.findAllWithAddresses(companyList);
	}
	
	@Transactional
	public void delete(Long id) {
		
		log.debug("called CompanyService.delete()");
		Company company= findById(id).orElseThrow(()->new NoSuchElementException());
		company.getAddresses().forEach(a -> a.setCompany(null));
		company.getEmployees().forEach(e -> e.setCompany(null));
		companyRepository.deleteById(id);
		
	}
	
	@Transactional
	public Company modify(Long id, Company changedCompany) {
		log.debug("called CompanyService.modify()");
		
		findById(id).orElseThrow(()->new NoSuchElementException());

		changedCompany.setCompanyId(id);
		
		changedCompany.getAddresses().forEach(a -> 
			{	a.setCompany(changedCompany);
				addressRepository.save(a);
			});
		
		changedCompany.getEmployees().forEach(e -> 
			{	e.setCompany(changedCompany);
				employeeRepository.save(e);
			});
		
		return companyRepository.save(changedCompany);
	}
		
	@Transactional
	public Company addEmployee(Long id, Employee employee) {
		log.debug("called CompanyService.addEmployee()");		
		
		Company company =  findById(id).orElseThrow(()->new NoSuchElementException());

		company.addEmployee(employee);
		
		employeeRepository.save(employee);
		
		//return companyRepository.save(company);
		return company;
	}
	@Transactional
	public Company deleteEmployee(Long id, Long employeeId) {
		log.debug("called CompanyService.deleteEmployee()");		
		
		Company company 	=  findById(id).orElseThrow(()->new NoSuchElementException());
		Employee employee	=  employeeRepository.findById(employeeId).orElseThrow(()->new NoSuchElementException());
		
		if (company.deleteEmployee(employee)) {
			employee.setCompany(null);
			employeeRepository.save(employee);
		}

		//return companyRepository.save(company);
		return company;
	}
	
	@Transactional
	public Company modifyEmployee(Long id, List<Employee> newEmployees) {
		log.debug("called CompanyService.modifyEmployee()");		
		
		Company company =  findById(id).orElseThrow(()->new NoSuchElementException());

		List<Employee> origEmployees = company.getEmployees();
		
		company.modifyEmployee(newEmployees);
		
		origEmployees.forEach(e -> 
			{
				e.setCompany(null);
				employeeRepository.save(e);
			});	
		
		newEmployees.forEach(e -> 
		{
			e.setCompany(company);
			employeeRepository.save(e);
		});	
		
		//return companyRepository.save(company);
		return company;
	}
}

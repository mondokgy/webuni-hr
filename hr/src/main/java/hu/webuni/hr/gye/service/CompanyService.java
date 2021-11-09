package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;

@Service
public class CompanyService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	private Map<Long, Company> companies = new HashMap<>();
	
	{		
		Address address1 = new Address(1L,"Budapest","1111","Kossuth utca","10","telephely");
		Address address2 = new Address(2L,"Szolnok","5000", "Petőfi utca", "5", "levelezés");
		
		List<Address> listAddress = new ArrayList<Address>();
		List<Employee> listEmployees = new ArrayList<Employee>();
		
		listAddress.add(address1);
		listAddress.add(address2);
		
		listEmployees.add(new Employee(1L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));		
		listEmployees.add(new Employee(2L,"Próba Róza", "tester", 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
		
		List<Employee> listEmployees2 = new ArrayList<Employee>(listEmployees);
		
		companies.put(1L,new Company(1L,"Kódoló Kft.", "123456-t", listEmployees, listAddress));		
		companies.put(2L,new Company(2L,"MindenIT Bt. ", "976578-a", listEmployees2, listAddress));
	}	
	public Company save(Company company) {
		companies.put(company.getCompanyId(), company);
		return company;
	}
	
	public List<Company> findAll(){
		return new ArrayList<>(companies.values());
	}
	
	public Company findById(Long id){
		return companies.get(id);
	}
		
	public Company delete(Long id) {
		
		if(findById(id)!=null) {	
			Company company = companies.get(id);
			companies.remove(id);
			return company;
		}
		else
			throw new NoSuchElementException();
	}
	
	public Company modify(Long id, Company changedCompany) {
		log.debug("called CompanyService.modify()");

		if(findById(id)!=null) {	
			changedCompany.setCompanyId(id);
			companies.put(id, changedCompany);
			Company company = companies.get(id);
			return company;
		}
		else
			throw new NoSuchElementException();
	}
		
}

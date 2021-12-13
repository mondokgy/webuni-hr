package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.config.HrDbInitConfigProperties;
import hu.webuni.hr.gye.config.HrDbInitConfigProperties.ConfigCompany;
import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.model.PositionDetail;
import hu.webuni.hr.gye.repository.AddressRepository;
import hu.webuni.hr.gye.repository.CompanyRepository;
import hu.webuni.hr.gye.repository.CompanyTypeRepository;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.PositionDetailRepository;
import hu.webuni.hr.gye.repository.PositionRepository;

@Service
public class InitDbService {
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;

	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	PositionDetailRepository positionDetailRepository;
	
	@Autowired
	HrDbInitConfigProperties configInitDb;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void clearDb(){		
		positionDetailRepository.deleteAll();
		companyRepository.deleteAll();		
		employeeRepository.deleteAll();
		addressRepository.deleteAll();
		companyTypeRepository.deleteAll();
		positionRepository.deleteAll();
		
	}
	
	public void insertTestData(){
		
		List<Address> addressList = new ArrayList<Address>();
		List<Employee> employeeList = new ArrayList<Employee>();

		List<Integer> addressCount = new ArrayList<Integer>();
		List<Integer> employeeCount = new ArrayList<Integer>();
		
		int companyPos = 0;
		
		configInitDb.getCompanyType().forEach(type -> companyTypeRepository.save(new CompanyType(null,type)));
		
		configInitDb.getPosition().forEach(position -> positionRepository.save(new Position(null,position.getName(),position.getMinEducation())));
		
		List<ConfigCompany> confCompanyList = configInitDb.getCompany();
		
		for(ConfigCompany confCompany: confCompanyList){
			addressCount.add(confCompany.getAddressCount());
			employeeCount.add(confCompany.getEmployeeCount());
			
			Company newCompany = new Company(null,confCompany.getName(), confCompany.getRegistrationNumber(), companyTypeRepository.findByType(confCompany.getType()), null, null);
			companyRepository.save(newCompany).getCompanyId();
		}
		
		configInitDb.getAddress().forEach(address -> {
			Address newAddress = new Address(null,address.getCity(),address.getZip(),address.getStreet(),address.getHouseNumber(),address.getType(),null);
			addressList.add(addressRepository.save(newAddress));
		});

		AtomicInteger postNick = new AtomicInteger(0);
		
		configInitDb.getEmployee().forEach(employee -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    	LocalDateTime startDate = LocalDateTime.parse(employee.getStartWork(), formatter);
			Employee newEmployee = new Employee(null,employee.getName(), positionRepository.findByName(employee.getPosition()), Integer.parseInt(employee.getSalary()) , startDate, null);
			if(postNick.get()==0) {
				newEmployee.setUsername("user");
			}else {
				newEmployee.setUsername("user"+postNick.toString());
			}
			postNick.getAndIncrement();
			newEmployee.setPassword(passwordEncoder.encode("pass"));
			employeeList.add(employeeRepository.save(newEmployee));
		});
		
		Employee manager = employeeList.get(0);
		
		for(Employee e: employeeList) {
			e.setManager(manager);
			employeeRepository.save(e);
		}

		List<Company> companyList = companyRepository.findAll();
		
		int allAddress = 0;
		int allEmployee=0;
		
		for(Company company: companyList ) {
			for(int k=0 ; k<addressCount.get(companyPos); k++ ) {
				Address address = addressList.get(k+allAddress);
				address.setCompany(company);
				addressRepository.save(address);
			}
			
			allAddress = allAddress + addressCount.get(companyPos);
			
			for(int k=0 ; k<employeeCount.get(companyPos); k++ ) {
				Employee employee = employeeList.get(k+allEmployee);
				employee.setCompany(company);
				employeeRepository.save(employee);
			}
			
			allEmployee = allEmployee + employeeCount.get(companyPos);
			
			companyPos++;
		}

		companyRepository.findAll().forEach(c -> {
			positionRepository.findAll().forEach(f -> {
				Random random = new Random();
				int salary = random.ints(1, 15).findAny().getAsInt() * 1000;
				PositionDetail pd = new PositionDetail(null,salary,c,f);
				positionDetailRepository.save(pd);
			});
			
		});
	}
}

package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.config.HrDbInitConfigProperties;
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

	public void clearDb(){		
		positionDetailRepository.deleteAll();
		companyRepository.deleteAll();		
		employeeRepository.deleteAll();
		addressRepository.deleteAll();
		companyTypeRepository.deleteAll();
		positionRepository.deleteAll();
		
	}
	
	public void insertTestData(){
		
		List<Long> addressIdList = new ArrayList<Long>();
		List<Long> employeeIdList = new ArrayList<Long>();

		configInitDb.getCompanyType().forEach(type -> companyTypeRepository.save(new CompanyType(null,type)));
		
		configInitDb.getPosition().forEach(position -> positionRepository.save(new Position(null,position.getName(),position.getMinEducation())));
		
		configInitDb.getAddress().forEach(address -> {
			Address newAddress = new Address(null,address.getCity(),address.getZip(),address.getStreet(),address.getHouseNumber(),address.getType());
			addressIdList.add(addressRepository.save(newAddress).getAddressId());
		});

		configInitDb.getEmployee().forEach(employee -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    	LocalDateTime startDate = LocalDateTime.parse(employee.getStartWork(), formatter);
			Employee newEmployee = new Employee(null,employee.getName(), positionRepository.findByName(employee.getPosition()), Integer.parseInt(employee.getSalary()) , startDate);
			employeeIdList.add(employeeRepository.save(newEmployee).getEmployeeID());
		});
		
		int[] addressListPos = {0};
		int[] employeeListPos = {0};
		
		configInitDb.getCompany().forEach(company -> {
			
			int addressPosShift = addressListPos[0];
			int emoployeePosShift = employeeListPos[0];
			
			List<Address> companyAddressList = new ArrayList<Address>();
			List<Employee> companyEmployeeList = new ArrayList<Employee>();
			
			int addressCount = company.getAddressCount();
			int employeeCount = company.getEmployeeCount();
			
			for(int k=0 ; k<addressCount; k++ ) {
				companyAddressList.add(addressRepository.getById(addressIdList.get(k+addressPosShift)));
				addressListPos[0] = addressListPos[0] + 1;
			}
			
			for(int l=0 ; l<employeeCount; l++ ) {
				companyEmployeeList.add(employeeRepository.getById(employeeIdList.get(l+emoployeePosShift)));
				employeeListPos[0] = employeeListPos[0] + 1;
			}
			
			Company newCompany = new Company(null,company.getName(), company.getRegistrationNumber(), companyTypeRepository.findByType(company.getType()), companyEmployeeList, companyAddressList);

			companyRepository.save(newCompany).getCompanyId();
		});
		
		
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

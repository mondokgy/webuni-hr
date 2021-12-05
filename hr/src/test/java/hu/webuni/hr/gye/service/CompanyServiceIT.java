package hu.webuni.hr.gye.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.AddressRepository;
import hu.webuni.hr.gye.repository.CompanyRepository;
import hu.webuni.hr.gye.repository.CompanyTypeRepository;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.PositionDetailRepository;
import hu.webuni.hr.gye.repository.PositionRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceIT {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	PositionDetailRepository positionDetailRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@BeforeEach
	public void init() {
		employeeRepository.deleteAll();
		addressRepository.deleteAll();
		positionDetailRepository.deleteAll();
		positionRepository.deleteAll();
		companyRepository.deleteAll();
		companyTypeRepository.deleteAll();
	}
	
	void initData() {
		
		CompanyType ct1 = companyTypeRepository.save(new CompanyType("KFT"));
		CompanyType ct2 = companyTypeRepository.save(new CompanyType("RT"));
//		CompanyType ct3 = companyTypeRepository.save(new CompanyType("ZRT"));
//		CompanyType ct4 = companyTypeRepository.save(new CompanyType("NYRT"));
		
		Position p1 = positionRepository.save(new Position("programmer","foiskola"));
		Position p2 = positionRepository.save(new Position("architect","egyetem"));
		Position p3 = positionRepository.save(new Position("tester","erettsegi"));
		
		companyRepository.save(new Company("Test Company1","RegNum123",ct1,null,null));
		companyRepository.save(new Company("Test Company2","RegNum987",ct2,null,null));
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		employeeRepository.save(new Employee("Test Employee1",p1,15000,dateTime));
		employeeRepository.save(new Employee("Test Employee2",p2,5000,dateTime.plusDays(50)));
		employeeRepository.save(new Employee("Test Employee3",p3,8000,dateTime.minusDays(50)));
		
	}
	
	@Test
	void testAddEmployeeToComapny() throws Exception{
		
		initData();
		
		List<Company> allCompanies = companyRepository.findAll();
		List<Employee> allEmployees = employeeRepository.findAll();
		
		Long testedCompanyId = allCompanies.get(0).getCompanyId();
		Employee testedEmployee = allEmployees.get(0);
		
		Company beforeCompany = companyRepository.findWithEmployeeById(testedCompanyId);
		
		List<Employee> beforeEmployees = beforeCompany.getEmployees();

		Company savedCompany = companyService.addEmployee(testedCompanyId, testedEmployee);
		
		Company afterCompany = companyRepository.findWithEmployeeById(savedCompany.getCompanyId());
		
		List<Employee> afterEmployees = afterCompany.getEmployees();

		assertThat(afterEmployees.size()).isEqualTo(beforeEmployees.size() + 1);
		assertThat(afterEmployees.get(afterEmployees.size()-1).getName())
			.isEqualTo(testedEmployee.getName());
	}

	
	@Test
	void testDeleteEmployeeFromComapny() throws Exception{

		testAddEmployeeToComapny();
		
		List<Company> allCompanies = companyRepository.findAll();
		Company testedCompany = companyRepository.findWithEmployeeById(allCompanies.get(0).getCompanyId());

		List<Employee> beforeEmployees = testedCompany.getEmployees();
				
		Employee testedEmployee = beforeEmployees.get(0);
		
		companyService.deleteEmployee(testedCompany.getCompanyId(), testedEmployee.getEmployeeID());
		
		Company afterCompany = companyRepository.findWithEmployeeById(testedCompany.getCompanyId());
		List<Employee> afterEmployees = afterCompany.getEmployees();
		
		assertThat(afterEmployees.size()).isEqualTo(beforeEmployees.size() - 1);
	}
	
	@Test
	void testReplaceEmployeeInComapny() throws Exception{

		testAddEmployeeToComapny();
		
		List<Company> allCompanies = companyRepository.findAll();
		Company testedCompany = companyRepository.findWithEmployeeById(allCompanies.get(0).getCompanyId());

		List<Employee> beforeEmployees = testedCompany.getEmployees();
		
		Employee testedEmployee = beforeEmployees.get(0);
		
		List<Employee> allEmployees = employeeRepository.findAll();
		
		allEmployees.removeIf(e -> e.getName() == testedEmployee.getName());
		
		companyService.modifyEmployee(testedCompany.getCompanyId(), allEmployees);
		
		Company afterCompany = companyRepository.findWithEmployeeById(testedCompany.getCompanyId());
		List<Employee> afterEmployees = afterCompany.getEmployees();
		
		assertThat(afterEmployees.size()).isEqualTo(allEmployees.size());
		assertThat(afterEmployees.get(0).getName())
			.isEqualTo(allEmployees.get(0).getName());
		assertThat(afterEmployees.get(1).getName())
			.isEqualTo(allEmployees.get(1).getName());
		
	}
}

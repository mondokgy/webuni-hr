package hu.webuni.hr.gye.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.CompanyRepository;
import hu.webuni.hr.gye.repository.CompanyTypeRepository;
import hu.webuni.hr.gye.repository.EmployeeRepository;
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
	
	@Test
	void testAddEmployeeToComapny() throws Exception{
		
		CompanyType ct1 = companyTypeRepository.save(new CompanyType("KFT"));
		CompanyType ct2 = companyTypeRepository.save(new CompanyType("RT"));
		CompanyType ct3 = companyTypeRepository.save(new CompanyType("ZRT"));
		CompanyType ct4 = companyTypeRepository.save(new CompanyType("NYRT"));
		
		Position p1 = positionRepository.save(new Position("programmer","foiskola"));
		Position p2 = positionRepository.save(new Position("architect","egyetem"));
		Position p3 = positionRepository.save(new Position("tester","erettsegi"));
		
		Company c1 = companyRepository.save(new Company("Test Company1","RegNum123",ct1,null,null));
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		Employee e1 = employeeRepository.save(new Employee("Test Employee1",p1,15000,dateTime));

		Company savedCompany = companyService.addEmployee(c1.getCompanyId(), e1);
		
		assertThat(savedCompany.getEmployees().get(0)).isEqualTo(e1);
	}
	
//	@Test
//	void testDeleteEmployeeFromComapny() throws Exception{
//		
//	}
//	
//	@Test
//	void testReplaceEmployeeInComapny() throws Exception{
//		
//	}
}

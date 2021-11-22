package hu.webuni.hr.gye.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.PositionDetail;
import hu.webuni.hr.gye.repository.CompanyRepository;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.PositionDetailRepository;

@Service
public class SalaryService {

	private EmployeeService employeeService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private EmployeeRepository employeeRepository;	
	@Autowired
	private PositionDetailRepository positionDetailRepository;

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	public SalaryService(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	public void setNewSalary(Employee employee) {
		log.debug("Start setNewSalary");
		//fizetés emelés százalékos értékét meghatározó szolgáltatás hívása
		int payRaisePercent;
		payRaisePercent = employeeService.getPayRaisePercent(employee);
		
		log.debug("Pay raise percent:" + payRaisePercent);
		//új fizetés meghatározása százalékos emelés alapján
		int newSalary =(int) (employee.getSalary()*((payRaisePercent+100)/100.0));
		
		log.debug("New salary:" + newSalary);
		//új fizetés beállítása
		employee.setSalary(newSalary);
		log.debug("Start setNewSalary");
	}
	
	@Transactional
	public void updateSalaryByPosition_BadEffort(String positionName, Integer salary, Long companyId) {
		log.debug("Start updateSalaryByPosition_BadEffort");

		List<Employee> employeeList = new ArrayList<Employee>();
		List<PositionDetail> posDetails = new ArrayList<PositionDetail>();
		
		if(companyId != null) {
			log.debug("find by company");
			posDetails = positionDetailRepository.findByPositionAndCompanyIdWithSalaryLowerThan(salary, positionName, companyId);
			employeeList = employeeRepository.findByCompanyAndPositionWithSalaryLowerThan(salary, companyId, positionName);
		}else {
			log.debug("find in all employee");
			posDetails = positionDetailRepository.findByPositionWithSalaryLowerThan(salary, positionName);
			employeeList = employeeRepository.findByPositionWithSalaryLowerThan(salary,positionName);
		}
			
		posDetails.forEach(pd -> {
			pd.setMinSalary(salary);
			log.debug("position detail: " + pd.toString());
		});
		
		employeeList.forEach(e -> {
			e.setSalary(salary);
			log.debug("employee: " + e.toString());
		});
		
		log.debug("End updateSalaryByPosition_BadEffort");
	}
	
	@Transactional
	public void updateSalaryByPosition_GoodEffort(String positionName, Integer salary, Long companyId) {
		log.debug("Start updateSalaryByPosition_GoodEffort");

		if(companyId != null) {
			employeeRepository.updateSalariesWithCompany(positionName, salary, companyId);
			positionDetailRepository.updatePositionMinSalaryWithCompany(positionName, salary, companyId);
		}else {
			employeeRepository.updateSalaries(positionName, salary);
			positionDetailRepository.updatePositionMinSalary(positionName, salary);
		}
		log.debug("End updateSalaryupdateSalaryByPosition_GoodEffortByPosition");
	}
	
}

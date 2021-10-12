package hu.webuni.hr.gye.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Employee;

@Service
public class SalaryService {

	private EmployeeService employeeService;
	
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
	
}

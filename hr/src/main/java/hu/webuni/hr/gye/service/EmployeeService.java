package hu.webuni.hr.gye.service;

import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Employee;

@Service
public interface EmployeeService {
	public int getPayRaisePercent(Employee employee);
}

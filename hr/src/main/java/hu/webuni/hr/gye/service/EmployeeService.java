package hu.webuni.hr.gye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Employee;

@Service
public interface EmployeeService{
	public int getPayRaisePercent(Employee employee);
	public List<Employee> findAll();
	public Employee save(Employee employee);
	public Employee findById(Long id);
	public Employee delete(Long id);
	public Employee modify(Long id, Employee employee);
}

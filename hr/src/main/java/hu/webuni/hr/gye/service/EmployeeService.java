package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.exception.TooManyRequestParamsException;
import hu.webuni.hr.gye.model.Employee;

@Service
public interface EmployeeService{
	public int getPayRaisePercent(Employee employee);
	public List<Employee> findAll();
	public List<Employee> findBy(Map<String,String> reqParams) throws TooManyRequestParamsException;
	public Page<Employee> findByPosition(String position, Pageable page);
	public Employee save(Employee employee);
	public Optional<Employee> findById(Long id);
	public Employee delete(Long id);
	public Employee modify(Long id, Employee employee);
}

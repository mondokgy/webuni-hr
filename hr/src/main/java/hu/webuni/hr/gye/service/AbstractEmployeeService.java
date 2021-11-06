package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Employee;

@Service
abstract class AbstractEmployeeService implements EmployeeService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	public Map<Long, Employee> employees = new HashMap<>();

	{		
		employees.put(1L,new Employee(1L,"Teszt Elek", "tester", 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40)));		
		employees.put(2L,new Employee(2L,"Próba Róza", "tester", 3000, LocalDateTime.of(2019,Month.MAY, 22, 19, 30, 40)));
	}
	@Override	
	public Employee save(Employee employee) {
		log.debug("called AbstractEmployeeService.save()");
		employees.put(employee.getEmployeeID(), employee);
		return employee;
	}
	@Override
	public List<Employee> findAll(){
		log.debug("called AbstractEmployeeService.findAll()");
		return new ArrayList<>(employees.values());
	}
	@Override
	public Employee findById(Long id){
		log.debug("called AbstractEmployeeService.findById()");
		return employees.get(id);
	}
	@Override	
	public Employee delete(Long id) {
		log.debug("called AbstractEmployeeService.delete()");
		Employee employee = employees.get(id);
		employees.remove(id);
		return employee;
	}
	@Override
	public Employee modify(Long id, Employee changedEmployee) {
		log.debug("called AbstractEmployeeService.modify()");
		changedEmployee.setEmployeeID(id);
		employees.put(id, changedEmployee);
		Employee employee = employees.get(id);
		return employee;
	}
}

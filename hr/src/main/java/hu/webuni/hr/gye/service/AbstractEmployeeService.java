package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Employee;

@Service
@NamedQuery(name="Employee.FindAll", query="SELECT e from EMPLOYEE e")
abstract class AbstractEmployeeService implements EmployeeService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public Employee save(Employee employee) {
		log.debug("called AbstractEmployeeService.save()");
		//employees.put(employee.getEmployeeID(), employee);
		em.persist(employee);
		return employee;
	}
	@Override
	public List<Employee> findAll(){
		log.debug("called AbstractEmployeeService.findAll()");
		return em.createQuery("Select e from Employee e", Employee.class).getResultList();
		//return new ArrayList<>(employees.values());
	}
	@Override
	public Employee findById(Long id){
		log.debug("called AbstractEmployeeService.findById()");
		return em.find(Employee.class, id);
	}
	@Override
	@Transactional
	public Employee delete(Long id) {
		log.debug("called AbstractEmployeeService.delete()");
//		Employee employee = employees.get(id);
//		employees.remove(id);
		Employee employee = findById(id);
		em.remove(employee);
		return employee;
	}
	@Override
	@Transactional
	public Employee modify(Long id, Employee changedEmployee) {

		log.debug("called AbstractEmployeeService.modify()");
		if(findById(id)!=null) {	
			changedEmployee.setEmployeeID(id);
			em.merge(changedEmployee);
			Employee employee = findById(id);
			return employee;
		}
		else
			throw new NoSuchElementException();
	}
}

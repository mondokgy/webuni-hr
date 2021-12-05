package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.NamedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.gye.exception.TooManyRequestParamsException;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.EmployeeRepository;
import hu.webuni.hr.gye.repository.PositionRepository;

@Service
@NamedQuery(name="Employee.FindAll", query="SELECT e from EMPLOYEE e")
abstract class AbstractEmployeeService implements EmployeeService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
//	@PersistenceContext
//	EntityManager em;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Override
	@Transactional
	public Employee save(Employee employee) {
		log.debug("called AbstractEmployeeService.save()");
		return employeeRepository.save(employee);
	}
	
	@Override
	public List<Employee> findAll(){
		log.debug("called AbstractEmployeeService.findAll()");
		return employeeRepository.findAll();
	}
	
	@Override
	public Optional<Employee> findById(Long id){
		log.debug("called AbstractEmployeeService.findById()");
		return employeeRepository.findById(id);
	}
	
	@Override
	@Transactional
	public Employee delete(Long id) {
		log.debug("called AbstractEmployeeService.delete()");
		Employee employee = findById(id)
				.orElseThrow(()->new NoSuchElementException());
		employeeRepository.deleteById(id);
		return employee;
	}
	
	@Override
	@Transactional
	public Employee modify(Long id, Employee changedEmployee) {

		log.debug("called AbstractEmployeeService.modify()");
		findById(id).orElseThrow(()->new NoSuchElementException());

		changedEmployee.setEmployeeID(id);
		
		return employeeRepository.save(changedEmployee);
		//return changedEmployee;

	}
	
	@Override
	public Page<Employee> findByPosition(String position, Pageable page){
		log.debug("called AbstractEmployeeService.findByPosition()");
		
		return employeeRepository.findByPosition(positionRepository.findByName(position), page);
	}

	private List<Employee> findByStartOfName(String namePrefix){
		log.debug("called AbstractEmployeeService.findByStartOfName()");
		return employeeRepository.findByNameStartingWithIgnoreCase(namePrefix);
	}
	
	private List<Employee> findByStartWorkBetween(LocalDateTime from, LocalDateTime to){
		log.debug("called AbstractEmployeeService.findByStartWorkBetween()");

		return employeeRepository.findByStartWorkBetween(from, to);
	}

	@Override
	public List<Employee> findBy(Map<String,String> reqParams) throws TooManyRequestParamsException{
		log.debug("called AbstractEmployeeService.findBy()");
		List<Employee> listEmployee = new ArrayList<Employee>();
		
		if(reqParams.isEmpty()) {
			log.debug("No parameters, return all employees");
			listEmployee = findAll();
		}else if(reqParams.size()>1) {
			log.debug("Too many request parameters.");
			throw new TooManyRequestParamsException("Too many request parameters.");
		}
		else {
			String paramName  ="";
			String paramValue ="";
			
			Iterator<Map.Entry<String, String>> itr = reqParams.entrySet().iterator();
	         
		    while(itr.hasNext()){
		    		Map.Entry<String, String> entry = itr.next();
		    		paramName  = entry.getKey();
		    		paramValue = entry.getValue();
		    }
			
		    switch(paramName) {
		    case "salary":
		    	listEmployee = findAll();
		    	log.debug("salary not null:*"+paramValue+"*, return filtered employees");
				
				List<Employee> candidateEmployee = new ArrayList<>();
				
				for(Employee employee : listEmployee) {
					log.debug("ciklus, id:*"+employee.getEmployeeID()+"*");
					if(employee.getSalary()>Long.parseLong(paramValue)) {
						log.debug("add to list.");
						candidateEmployee.add(employee);
					}
				}

				listEmployee = candidateEmployee;
				break;
		    case "namePrefix":
		    	log.debug("namePrefix not null:*"+paramValue+"*, return filtered employees");
		    	listEmployee = findByStartOfName(paramValue);
			    break;
		    case "startDate":
		    	log.debug("startDate not null:*"+paramValue+"*, return filtered employees");

		    	String[] reqParamArray = paramValue.split("_",2);
		    	log.debug("from:*"+reqParamArray[0]+"*");
		    	log.debug("to:*"+reqParamArray[1]+"*");

		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		    	LocalDateTime startFrom = LocalDateTime.parse(reqParamArray[0], formatter);
		    	LocalDateTime startTo = LocalDateTime.parse(reqParamArray[1], formatter);

		    	listEmployee = findByStartWorkBetween(startFrom,startTo);
			    break;
		    default:
		    	listEmployee = findAll();
		  }
		}
	    
		return listEmployee;
	}
	
	@Override
	public List<Employee> findEmployeeByExample(Employee example){
				
		Long id 				= example.getEmployeeID();
		String name 			= example.getName();
		Position position 		= example.getPosition();
		Integer salary 			= example.getSalary();
		LocalDateTime stratDate = example.getStartWork();
		Company company 		= example.getCompany();
		
		Specification <Employee> spec = Specification.where(null);
		
		if(id>0) {
			spec = spec.and(EmployeeSpecifications.hasId(id));
		}
		if(StringUtils.hasText(name)) {
			spec = spec.and(EmployeeSpecifications.hasName(name));
		}
		if(position !=null && StringUtils.hasText(position.getName())) {
			spec = spec.and(EmployeeSpecifications.hasPosition(position));
		}
		if(salary>0) {
			spec = spec.and(EmployeeSpecifications.hasSalary(salary));
		}
		if(company !=null && StringUtils.hasText(company.getName())) {
			spec = spec.and(EmployeeSpecifications.hasCompanyName(company.getName()));
		}
		if(stratDate != null) {
			spec = spec.and(EmployeeSpecifications.hasStartDate(stratDate));
		}
		return employeeRepository.findAll(spec);
	}
}

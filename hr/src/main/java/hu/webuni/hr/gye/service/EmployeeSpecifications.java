package hu.webuni.hr.gye.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Employee_;

public class EmployeeSpecifications {
	public static Specification<Employee> hasId(Long id){
		return (root,cq,cb) -> cb.equal(root.get(Employee_.employeeID), id);
	}
	public static Specification<Employee> hasName(String name){
		return (root,cq,cb) -> cb.like(cb.upper(root.get(Employee_.name)), StringUtils.upperCase(name + "%") );
	}
}

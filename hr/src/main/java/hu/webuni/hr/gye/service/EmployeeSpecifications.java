package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.gye.model.Company_;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Employee_;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.model.Position_;

public class EmployeeSpecifications {
	public static Specification<Employee> hasId(Long id){
		return (root,cq,cb) -> cb.equal(root.get(Employee_.employeeID), id);
	}
	public static Specification<Employee> hasName(String name){
		return (root,cq,cb) -> cb.like(cb.upper(root.get(Employee_.name)), StringUtils.upperCase(name) + "%" );
	}
	public static Specification<Employee> hasPosition(Position position) {
		return (root,cq,cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), position.getName());
	}
	public static Specification<Employee> hasSalary(Integer salary) {
		Integer minSalary = (int) (salary*0.95);
		Integer maxSalary = (int) (salary*1.05);
		return (root,cq,cb) -> cb.between(root.get(Employee_.salary), minSalary, maxSalary);
	}
	public static Specification<Employee> hasStartDate(LocalDateTime startDate) {
		LocalDateTime start = LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(0, 0));
		LocalDateTime end = start.plusDays(1);
		return (root,cq,cb) -> cb.between(root.get(Employee_.startWork), start, end);
	}
	public static Specification<Employee> hasCompanyName(String companyName) {
		return (root,cq,cb) -> cb.like(cb.upper(root.get(Employee_.company)
										.get(Company_.name)), 
										StringUtils.upperCase(companyName) + "%" );
	}
}

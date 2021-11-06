package hu.webuni.hr.gye.model;

import java.util.List;

import hu.webuni.hr.gye.dto.EmployeeDto;

public class Company {

	Long companyId;
	String name;
	String registrationNumber;
	List<Employee> employees;
	List<Address> adresses;	

	public Company(Long companyId, String name, String registrationNumber, List<Employee> employees, List<Address> addresses) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.adresses = addresses;
		this.employees = employees;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public List<Address> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}			
}

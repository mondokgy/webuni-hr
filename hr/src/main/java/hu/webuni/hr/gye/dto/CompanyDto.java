package hu.webuni.hr.gye.dto;

import java.util.List;

import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Company.CompanyType;
import hu.webuni.hr.gye.model.Employee;

public class CompanyDto {

	Long companyId;
	String name;
	String registrationNumber;
	List<Employee> employees;
	List<Address> addresses;
	CompanyType type;

	public CompanyDto(Long companyId, String name, String registrationNumber, List<Employee> employees, List<Address> addresses) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.addresses = addresses;
		this.employees = employees;
	}
	
//	public CompanyDto(CompanyDto otherCompany) {
//		super();
//		this.companyId = otherCompany.getCompanyId();
//		this.name = otherCompany.getName();
//		this.registrationNumber = otherCompany.getRegistrationNumber();
//		this.adresses = otherCompany.getAdresses();
//		this.employees = otherCompany.getEmployees();
//	}
	
	public Long getCompanyId() {
		return companyId;
	}
	public CompanyType getType() {
		return type;
	}

	public void setType(CompanyType type) {
		this.type = type;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}	
	
	
}

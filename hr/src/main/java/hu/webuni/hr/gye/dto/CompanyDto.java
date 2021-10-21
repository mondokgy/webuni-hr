package hu.webuni.hr.gye.dto;

import java.util.List;

import hu.webuni.hr.gye.model.Address;

public class CompanyDto {

	Long companyId;
	String name;
	String registrationNumber;
	List<EmployeeDto> employees;
	List<Address> adresses;

	public CompanyDto(Long companyId, String name, String registrationNumber, List<EmployeeDto> employees, List<Address> adesress) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.adresses = adesress;
		this.employees = employees;
	}
	
	public CompanyDto(CompanyDto otherCompany) {
		super();
		this.companyId = otherCompany.getCompanyId();
		this.name = otherCompany.getName();
		this.registrationNumber = otherCompany.getRegistrationNumber();
		this.adresses = otherCompany.getAdresses();
		this.employees = otherCompany.getEmployees();
	}
	
	public Long getCompanyId() {
		return companyId;
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
	public List<Address> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}
	public List<EmployeeDto> getEmployees() {
		return employees;
	}
	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}	
	
	
}

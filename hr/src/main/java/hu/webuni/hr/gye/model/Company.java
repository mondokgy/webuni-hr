package hu.webuni.hr.gye.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Company {
	
	@Id
	@GeneratedValue
	Long companyId;
	
	String name;
	String registrationNumber;
	
	@OneToMany
	@JoinTable(name="COMPANY_EMPLOYEE_JOIN", 
		joinColumns = {@JoinColumn(name="COMPANY_FK")}, 
		inverseJoinColumns = {@JoinColumn(name="EMPLOYEE_FK")})
	List<Employee> employees;
	
	@OneToMany
	@JoinTable(name="COMPANY_ADDRESS_JOIN", 
		joinColumns = {@JoinColumn(name="COMPANY_FK")}, 
		inverseJoinColumns = {@JoinColumn(name="ADDRESS_FK")})
	List<Address> addresses;	

	public Company() {
		
	}
	
	public Company(Long companyId, String name, String registrationNumber, List<Employee> employees, List<Address> addresses) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.addresses = addresses;
		this.employees = employees;
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
		return addresses;
	}
	public void setAdresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", registrationNumber=" + registrationNumber
				+ ", employees=" + employees + ", addresses=" + addresses + "]";
	}			
}

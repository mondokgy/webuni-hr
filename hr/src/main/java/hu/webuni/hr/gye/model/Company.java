package hu.webuni.hr.gye.model;

import java.util.List;

public class Company {

	Long companyId;
	String name;
	String registrationNumber;
	

	public Company(Long companyId, String name, String registrationNumber) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
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
		
}

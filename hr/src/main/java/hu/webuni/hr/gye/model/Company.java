package hu.webuni.hr.gye.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="COMPANY_SEQUENCE_GENERATOR", sequenceName="COMPANY_SEQUENCE", initialValue=1, allocationSize=10)
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANY_SEQUENCE_GENERATOR")
	Long companyId;
	
	String name;
	String registrationNumber;
	
//	@Enumerated(EnumType.STRING)
//	@Column(name = "type", length = 5)
	@ManyToOne
	@JoinColumn(name ="COMPANYTYPE_ID")
	CompanyType type;
	
	@OneToMany(mappedBy = "company")
	List<Employee> employees;
	
	@OneToMany(mappedBy = "company")
	List<Address> addresses;	

	public Company() {
		
	}
	
	
	public Company(Long companyId, String name, String registrationNumber, CompanyType type, List<Employee> employees,
			List<Address> addresses) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.type = type;
		this.employees = employees;
		this.addresses = addresses;
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
	public CompanyType getType() {
		return type;
	}
	public void setType(CompanyType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", registrationNumber=" + registrationNumber
				+ ", type=" + type + ", employees=" + employees + ", addresses=" + addresses + "]";
	}

// ENUM helyett entitás
//	public enum CompanyType {
//	    KFT,BT,RT,NYRT
//	}
}


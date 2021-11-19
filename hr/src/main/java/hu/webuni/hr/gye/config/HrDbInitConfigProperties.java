package hu.webuni.hr.gye.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import hu.webuni.hr.gye.model.CompanyType;

@ConfigurationProperties(prefix="hr.db.init")
@Component
public class HrDbInitConfigProperties {

	private List<Address> address = new ArrayList<Address>();
	
	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public static class Address{
		private String city;
		private String zip;
		private String street;
		private String houseNumber;
		private String type;
		
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
		
		private List<Employee> employee = new ArrayList<Employee>();
		
		public List<Employee> getEmployee() {
			return employee;
		}

		public void setEmployee(List<Employee> employee) {
			this.employee = employee;
		}
		
		public static class Employee{
			private String name;
			private String position;
			private String salary;
			private String startWork;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPosition() {
				return position;
			}
			public void setPosition(String position) {
				this.position = position;
			}
			public String getSalary() {
				return salary;
			}
			public void setSalary(String salary) {
				this.salary = salary;
			}
			public String getStartWork() {
				return startWork;
			}
			public void setStartWork(String startWork) {
				this.startWork = startWork;
			}
		
		
		}
		
		private List<Company> company = new ArrayList<Company>();
		
		public List<Company> getCompany() {
			return company;
		}

		public void setCompany(List<Company> company) {
			this.company = company;
		}

		public static class Company{
			private String name;
			private String registrationNumber;
			private String type;
			private Integer addressCount;
			private Integer employeeCount;
						
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
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
			public Integer getAddressCount() {
				return addressCount;
			}
			public void setAddressCount(Integer addressCount) {
				this.addressCount = addressCount;
			}
			public Integer getEmployeeCount() {
				return employeeCount;
			}
			public void setEmployeeCount(Integer employeeCount) {
				this.employeeCount = employeeCount;
			}
			
		}
		
		private List<String> companyType = new ArrayList<String>();
		
		public List<String> getCompanyType() {
			return companyType;
		}

		public void setCompanyType(List<String> companyType) {
			this.companyType = companyType;
		}
		
		private List<Position> position = new ArrayList<Position>();
		
		public List<Position> getPosition() {
			return position;
		}

		public void setPosition(List<Position> position) {
			this.position = position;
		}
		
		public static class Position{
			private String name;
			private String minEducation;
			private Integer minSalary;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getMinEducation() {
				return minEducation;
			}
			public void setMinEducation(String minEducation) {
				this.minEducation = minEducation;
			}
			public Integer getMinSalary() {
				return minSalary;
			}
			public void setMinSalary(Integer minSalary) {
				this.minSalary = minSalary;
			}
	
		}
		
}


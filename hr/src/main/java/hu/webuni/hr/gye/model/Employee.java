package hu.webuni.hr.gye.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Entity
@Component
@SequenceGenerator(name="EMPLOYEE_SEQUENCE_GENERATOR", sequenceName="EMPLOYEE_SEQUENCE", initialValue=1, allocationSize=10)
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYEE_SEQUENCE_GENERATOR")
	Long employeeID;
	
	String name;
	
	@ManyToOne
	@JoinColumn(name ="POSITION_ID")
	Position position;
	Integer salary;
	LocalDateTime startWork;	
	
	@ManyToOne
	Company company;
	
	public Employee() {

	}
	
	public Employee(Long employeeID, String name, Position position, Integer salary, LocalDateTime startWork, Company company) {
			this.employeeID = employeeID;
			this.name = name;
			this.position = position;
			this.salary = salary;
			this.startWork = startWork;
			this.company = company;
		}
	
	public Long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public LocalDateTime getStartWork() {
		return startWork;
	}
	public void setStartWork(LocalDateTime startWork) {
		this.startWork = startWork;
	}	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", name=" + name + ", position=" + position + ", salary=" + salary
				+ ", startWork=" + startWork + ", company=" + company + "]";
	} 
}

package hu.webuni.hr.gye.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	String position;
	Integer salary;
	LocalDateTime startWork;	
		
	public Employee() {

	}
	
	public Employee(Long employeeID, String name, String position, Integer salary, LocalDateTime startWork) {
			this.employeeID = employeeID;
			this.name = name;
			this.position = position;
			this.salary = salary;
			this.startWork = startWork;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
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

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", name=" + name + ", position=" + position + ", salary=" + salary
				+ ", startWork=" + startWork + "]";
	} 
}

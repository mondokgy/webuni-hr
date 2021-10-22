package hu.webuni.hr.gye.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class Employee {
	
	Long employeeID;
	String name;
	String position;
	Integer salary;
	LocalDateTime startWork;	
		
	public Employee() {
		this.employeeID = 1L;

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

	public String toString() { 
	    return "Name: '" + this.name + "', Employee Id: '" + this.employeeID + "', Position: '" + this.position + "'" + "', Salary: '" + this.salary + "'" + "', Working start: '" + this.startWork.toString() + "'";
	} 
}

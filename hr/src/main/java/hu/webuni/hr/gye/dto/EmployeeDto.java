package hu.webuni.hr.gye.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

public class EmployeeDto {
	private Long employeeID;
	@NotEmpty(message = "Name cannot be null")
	private String name;
	@NotEmpty(message = "Position cannot be null")
	private String position;
	@Positive(message = "Position cannot be null")
	private Integer salary;
	@Past
	private LocalDateTime startWork;
	
	public EmployeeDto() {
		
	}
	
	public EmployeeDto(Long employeeID, String name, String position, Integer salary, LocalDateTime startWork) {
		super();
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

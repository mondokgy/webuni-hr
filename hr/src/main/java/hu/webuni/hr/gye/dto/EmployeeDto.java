package hu.webuni.hr.gye.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import hu.webuni.hr.gye.model.Position;

public class EmployeeDto {
	private Long employeeID;
	@NotEmpty(message = "Name cannot be null")
	private String name;
	@NotEmpty(message = "Position cannot be null")
	private Position position;
	@Positive(message = "Position cannot be null")
	private Integer salary;
	@Past
	private LocalDateTime startWork;
	
	public EmployeeDto() {
		
	}
	
	public EmployeeDto(Long employeeID, String name, Position position, Integer salary, LocalDateTime startWork) {
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
	
	
	@Override
	public String toString() {
		return "EmployeeDto [employeeID=" + employeeID + ", name=" + name + ", position=" + position + ", salary="
				+ salary + ", startWork=" + startWork + "]";
	} 
}

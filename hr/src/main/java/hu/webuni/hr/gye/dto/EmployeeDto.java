package hu.webuni.hr.gye.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
	private Long employeeID;
	private String name;
	private String position;
	private Integer salary;
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
	
	
}

package hu.webuni.hr.gye.dto;

import hu.webuni.hr.gye.model.Position;

public class AvgSalaryByPosition {

	double salary;
	Position position;
		
	public AvgSalaryByPosition(double salary, Position position) {
		super();
		this.salary = salary;
		this.position = position;
	}
	
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "AvgSalaryByPosition [salary=" + salary + ", position=" + position + "]";
	}
	
	
	
	
}

package hu.webuni.hr.gye.dto;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Position;

public class PositionDetailDto {
	
	private Long positionDetailID;
	private Integer minSalary;
	private CompanyDto company;
	private Position position;
	
	public PositionDetailDto(Long positionDetailID, Integer minSalary, CompanyDto company, Position position) {
		super();
		this.positionDetailID = positionDetailID;
		this.minSalary = minSalary;
		this.company = company;
		this.position = position;
	}
	
	public Long getPositionDetailID() {
		return positionDetailID;
	}
	public void setPositionDetailID(Long positionDetailID) {
		this.positionDetailID = positionDetailID;
	}
	public Integer getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}
	public CompanyDto getCompany() {
		return company;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "PositionDetailDto [positionDetailID=" + positionDetailID + ", minSalary=" + minSalary + ", company="
				+ company + ", position=" + position + "]";
	}
	
	
}

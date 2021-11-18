package hu.webuni.hr.gye.dto;

public class PositionDto {
	Long positionID;	
	String name;
	String minEducation;
	Long minSalary;
	
	public Long getPositionID() {
		return positionID;
	}
	public void setPositionID(Long positionID) {
		this.positionID = positionID;
	}
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
	public Long getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(Long minSalary) {
		this.minSalary = minSalary;
	}
	
	public PositionDto(Long positionID, String name, String minEducation, Long minSalary) {
		super();
		this.positionID = positionID;
		this.name = name;
		this.minEducation = minEducation;
		this.minSalary = minSalary;
	}
	
	
}

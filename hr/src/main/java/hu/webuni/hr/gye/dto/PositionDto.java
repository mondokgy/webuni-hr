package hu.webuni.hr.gye.dto;

public class PositionDto {
	Long positionID;	
	String name;
	String minEducation;
	
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

	public PositionDto(Long positionID, String name, String minEducation) {
		super();
		this.positionID = positionID;
		this.name = name;
		this.minEducation = minEducation;
	}
	
	@Override
	public String toString() {
		return "PositionDto [positionID=" + positionID + ", name=" + name + ", minEducation=" + minEducation + "]";
	}

	
	
}

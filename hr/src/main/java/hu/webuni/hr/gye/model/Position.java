package hu.webuni.hr.gye.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="POSITION_SEQUENCE_GENERATOR", sequenceName="POSITION_SEQUENCE", initialValue=1, allocationSize=10)
public class Position {

	@Id
	@Column(name ="POSITION_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="POSITION_SEQUENCE_GENERATOR")
	Long positionID;
	
	String name;
	String minEducation;

	public Position() {

	}
	
	public Position(Long positionID, String name, String minEducation) {
		super();
		this.positionID = positionID;
		this.name = name;
		this.minEducation = minEducation;
	}
	
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

	
	@Override
	public String toString() {
		return "Position [positionID=" + positionID + ", name=" + name + ", minEducation=" + minEducation
				+ "]";
	}

	
}

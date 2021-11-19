package hu.webuni.hr.gye.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="POSITIONDETAIL_SEQUENCE_GENERATOR", sequenceName="POSITIONDETAIL_SEQUENCE", initialValue=1, allocationSize=10)
public class PositionDetail {

	@Id
	@Column(name ="POSITIONDETAIL_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="POSITIONDETAIL_SEQUENCE_GENERATOR")
	private Long positionDetailID;
	
	private Integer minSalary;
	@ManyToOne
	private Company company;
	@ManyToOne
	private Position position;
		
	public PositionDetail() {
		
	}
	
	public PositionDetail(Long positionDetailID, Integer minSalary, Company company, Position position) {
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
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
		return "PositionDetail [positionDetailID=" + positionDetailID + ", minSalary=" + minSalary + ", company="
				+ company + ", position=" + position + "]";
	}	

}

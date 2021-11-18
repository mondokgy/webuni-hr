package hu.webuni.hr.gye.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="COMPANYTYPE_SEQUENCE_GENERATOR", sequenceName="COMPANYTYPE_SEQUENCE", initialValue=1, allocationSize=10)
public class CompanyType {
	@Id
	@Column(name ="COMPANYTYPE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANYTYPE_SEQUENCE_GENERATOR")
	Long companyTypeId;
	
	String type;

	public CompanyType() {
		
	}
	
	public CompanyType(Long companyTypeId, String type) {
		super();
		this.companyTypeId = companyTypeId;
		this.type = type;
	}

	public Long getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(Long companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CompanyType [companyTypeId=" + companyTypeId + ", type=" + type + "]";
	}
	
	
}

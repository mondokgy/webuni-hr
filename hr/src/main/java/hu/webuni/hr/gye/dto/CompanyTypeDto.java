package hu.webuni.hr.gye.dto;

public class CompanyTypeDto {
	Long companyTypeId;	
	String type;
	
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
	
	public CompanyTypeDto(Long companyTypeId, String type) {
		super();
		this.companyTypeId = companyTypeId;
		this.type = type;
	}
	
	

}

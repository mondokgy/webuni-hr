package hu.webuni.hr.gye.dto;

import java.time.LocalDateTime;

public class HolidaysDto {

	private Long holidaysId;
		
	private EmployeeDto createdBy;
	private LocalDateTime createdDate;
	
	private EmployeeDto approveBy;
	private LocalDateTime approveDate;
	
	private LocalDateTime holidayStart;
	private LocalDateTime holidayEnd;
	
	private String status;
	private LocalDateTime statusDate;

	HolidaysDto(){
		
	}
	
	public HolidaysDto(Long holidaysId, EmployeeDto createdBy, LocalDateTime createdDate, EmployeeDto approveBy,
			LocalDateTime approveDate, LocalDateTime holidayStart, LocalDateTime holidayEnd, String status,
			LocalDateTime statusDate) {
		super();
		this.holidaysId = holidaysId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approveBy = approveBy;
		this.approveDate = approveDate;
		this.holidayStart = holidayStart;
		this.holidayEnd = holidayEnd;
		this.status = status;
		this.statusDate = statusDate;
	}

	public Long getHolidaysId() {
		return holidaysId;
	}

	public void setHolidaysId(Long holidaysId) {
		this.holidaysId = holidaysId;
	}

	public EmployeeDto getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDto createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDto getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(EmployeeDto approveBy) {
		this.approveBy = approveBy;
	}

	public LocalDateTime getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(LocalDateTime approveDate) {
		this.approveDate = approveDate;
	}

	public LocalDateTime getHolidayStart() {
		return holidayStart;
	}

	public void setHolidayStart(LocalDateTime holidayStart) {
		this.holidayStart = holidayStart;
	}

	public LocalDateTime getHolidayEnd() {
		return holidayEnd;
	}

	public void setHolidayEnd(LocalDateTime holidayEnd) {
		this.holidayEnd = holidayEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(LocalDateTime statusDate) {
		this.statusDate = statusDate;
	}

	@Override
	public String toString() {
		return "HolidaysDto [holidaysId=" + holidaysId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", approveBy=" + approveBy + ", approveDate=" + approveDate + ", holidayStart=" + holidayStart
				+ ", holidayEnd=" + holidayEnd + ", status=" + status + ", statusDate=" + statusDate + "]";
	}
	
	
}

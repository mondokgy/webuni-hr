package hu.webuni.hr.gye.dto;

import java.time.LocalDateTime;

public class HolidaysDto {

	private Long holidaysId;
		
	private Long employeeId;
	private LocalDateTime createdDate;
	
	private Long approverId;
	private LocalDateTime approveDate;
	
	private LocalDateTime holidayStart;
	private LocalDateTime holidayEnd;
	
	private String status;
	private LocalDateTime statusDate;

	HolidaysDto(){
		
	}
	
	public HolidaysDto(Long holidaysId, Long employeeId, LocalDateTime createdDate, Long approverId,
			LocalDateTime approveDate, LocalDateTime holidayStart, LocalDateTime holidayEnd, String status,
			LocalDateTime statusDate) {
		super();
		this.holidaysId = holidaysId;
		this.employeeId = employeeId;
		this.createdDate = createdDate;
		this.approverId = approverId;
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

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
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
		return "HolidaysDto [holidaysId=" + holidaysId + ", employeeId=" + employeeId + ", createdDate=" + createdDate
				+ ", approverId=" + approverId + ", approveDate=" + approveDate + ", holidayStart=" + holidayStart
				+ ", holidayEnd=" + holidayEnd + ", status=" + status + ", statusDate=" + statusDate + "]";
	}
	
	
}

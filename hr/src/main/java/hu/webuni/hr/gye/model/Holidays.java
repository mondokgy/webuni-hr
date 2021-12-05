package hu.webuni.hr.gye.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="HOLIDAYS_SEQUENCE_GENERATOR", sequenceName="HOLIDAYS_SEQUENCE", initialValue=1, allocationSize=10)
public class Holidays {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HOLIDAYS_SEQUENCE_GENERATOR")
	private Long holidaysId;
	
	@ManyToOne
	private Employee createdBy;
	private LocalDateTime createdDate;
	
	@ManyToOne
	private Employee approveBy;
	private LocalDateTime approveDate;
	
	private LocalDateTime holidayStart;
	private LocalDateTime holidayEnd;
	
	private String status;
	private LocalDateTime statusDate;
	
	public Holidays() {
		
	}
	
	public Holidays(Long holidaysId, Employee createdBy, LocalDateTime createdDate, Employee approveBy,
			LocalDateTime approveDate, LocalDateTime holidayStart, LocalDateTime holidayEnd, String status,
			LocalDateTime statusDate) {

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

	public Employee getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Employee createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Employee getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(Employee approveBy) {
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
		return "Holidays [holidaysId=" + holidaysId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", approveBy=" + approveBy + ", approveDate=" + approveDate + ", holidayStart=" + holidayStart
				+ ", holidayEnd=" + holidayEnd + ", status=" + status + ", statusDate=" + statusDate + "]";
	}
	
}

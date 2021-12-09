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
	private Employee approvedBy;
	private LocalDateTime approveDate;
	
	private LocalDateTime holidayStart;
	private LocalDateTime holidayEnd;
	
	private HolidayStatus status;
	private LocalDateTime statusDate;
	
	public Holidays() {
		
	}
	
	public Holidays(Long holidaysId, Employee createdBy, LocalDateTime createdDate, Employee approveBy,
			LocalDateTime approveDate, LocalDateTime holidayStart, LocalDateTime holidayEnd, HolidayStatus status,
			LocalDateTime statusDate) {

		this.holidaysId = holidaysId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approveBy;
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

	public Employee getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Employee approvedBy) {
		this.approvedBy = approvedBy;
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

	public HolidayStatus getStatus() {
		return status;
	}

	public void setStatus(HolidayStatus status) {
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
				+ ", approvedBy=" + approvedBy + ", approveDate=" + approveDate + ", holidayStart=" + holidayStart
				+ ", holidayEnd=" + holidayEnd + ", status=" + status + ", statusDate=" + statusDate + "]";
	}
	
	public enum HolidayStatus {
		NEW, DELETED, APPROVED, REJECTED;
	}
}

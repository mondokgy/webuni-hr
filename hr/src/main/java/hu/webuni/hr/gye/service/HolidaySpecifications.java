package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.gye.model.Employee_;
import hu.webuni.hr.gye.model.Holidays;
import hu.webuni.hr.gye.model.Holidays.HolidayStatus;
import hu.webuni.hr.gye.model.Holidays_;

public class HolidaySpecifications {


	public static Specification<Holidays> hasStatus(String status) {
		
		return (root, cq, cb) -> cb.equal(root.get(Holidays_.status), HolidayStatus.valueOf(status));
	}

	public static Specification<Holidays> hasEmployee(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holidays_.createdBy).get(Employee_.name)),
				(name + "%").toLowerCase());
	}

	public static Specification<Holidays> hasApprover(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holidays_.approvedBy).get(Employee_.name)),
				(name + "%").toLowerCase());
	}

	public static Specification<Holidays> hasCreatedDate(LocalDateTime createddate) {
		return (root, cq, cb) ->  cb.between(root.get(Holidays_.createdDate), createddate.minusDays(1), createddate.plusDays(1));
	}

	public static Specification<Holidays> hasHolidayDatesStartPeriod(LocalDateTime stratDate) {
		return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Holidays_.holidayEnd), stratDate);
	}

	public static Specification<Holidays> hasHolidayDatesEndPeriod(LocalDateTime endDate) {
		return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Holidays_.holidayStart), endDate);
	}


}

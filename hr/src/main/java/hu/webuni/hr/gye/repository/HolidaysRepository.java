package hu.webuni.hr.gye.repository;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;

public interface HolidaysRepository extends JpaRepository<Holidays, Long>{
	Page<Holidays> findAll(Pageable page);

	Page<Holidays> findAll(Specification<Holidays> spec, Pageable page);
	
	@Query("SELECT distinct h FROM Holidays h LEFT JOIN FETCH h.createdBy where h.holidaysId = :Id")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public Holidays findWithEmployeeById(Long Id);
}

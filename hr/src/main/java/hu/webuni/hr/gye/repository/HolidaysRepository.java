package hu.webuni.hr.gye.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;

public interface HolidaysRepository extends JpaRepository<Holidays, Long>{
	Page<Holidays> findAll(Pageable page);

	Page<Holidays> findAll(Specification<Holidays> spec, Pageable page);
}

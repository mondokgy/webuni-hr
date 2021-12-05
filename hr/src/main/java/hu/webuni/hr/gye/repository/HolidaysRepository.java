package hu.webuni.hr.gye.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.Holidays;

public interface HolidaysRepository extends JpaRepository<Holidays, Long>{

}

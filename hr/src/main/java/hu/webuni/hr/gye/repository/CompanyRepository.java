package hu.webuni.hr.gye.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
}

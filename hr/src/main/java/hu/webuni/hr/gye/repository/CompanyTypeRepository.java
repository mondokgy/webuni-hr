package hu.webuni.hr.gye.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.CompanyType;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long>{
	CompanyType findByType(String type);
}

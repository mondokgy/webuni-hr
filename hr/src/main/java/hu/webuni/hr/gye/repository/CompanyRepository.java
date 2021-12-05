package hu.webuni.hr.gye.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import hu.webuni.hr.gye.dto.AvgSalaryByPosition;
import hu.webuni.hr.gye.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	//@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :minSalary")
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.employees e where e.salary > :minSalary")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public List<Company> findByEmployeeWithSalaryHigherThan(int minSalary);
	
	//@Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :minEmployeeCount")
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.employees  where SIZE(c.employees) > :minEmployeeCount")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);

	@Query("SELECT new  hu.webuni.hr.gye.dto.AvgSalaryByPosition(avg(e.salary),e.position)"
			+ "FROM Company c INNER JOIN c.employees e  WHERE c.id = :companyId GROUP BY e.position ORDER BY avg(e.salary) DESC")
	public List<AvgSalaryByPosition> findAverageSalariesByPosition(long companyId);
	
	
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.employees")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public List<Company> findAllWithEmployee();
	
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.addresses where c in :clist")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public List<Company> findAllWithAddresses(List<Company> clist);
	
	
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.employees where c.companyId = :companyId")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public Company findWithEmployeeById(Long companyId);
	
	@Query("SELECT distinct c FROM Company c LEFT JOIN FETCH c.addresses where c = :company")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public Company findWithAddressesById(Company company);
}


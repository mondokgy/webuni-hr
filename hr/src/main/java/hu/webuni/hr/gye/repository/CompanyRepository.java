package hu.webuni.hr.gye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gye.dto.AvgSalaryByPosition;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :minSalary")
	public List<Company> findByEmployeeWithSalaryHigherThan(int minSalary);
	
	@Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :minEmployeeCount")
	public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);

	@Query("SELECT new  hu.webuni.hr.gye.dto.AvgSalaryByPosition(avg(e.salary),e.position)"
			+ "FROM Company c INNER JOIN c.employees e  WHERE c.id = :companyId GROUP BY e.position ORDER BY avg(e.salary) DESC")
	public List<AvgSalaryByPosition> findAverageSalariesByPosition(long companyId);
	
	@Query("SELECT DISTINCT e FROM Company c JOIN c.employees e "
			+ "WHERE e.salary < :salary and c.companyId = :companyId and e.position.name = :positionName")
	public List<Employee> findByCompanyAndPositionWithSalaryLowerThan(Integer salary, Long companyId, String positionName);

}


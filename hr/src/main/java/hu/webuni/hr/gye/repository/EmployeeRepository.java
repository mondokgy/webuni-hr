package hu.webuni.hr.gye.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
	
	//select * from employee where position = ?1
	Page<Employee> findByPosition(Position position, Pageable page);
	//select * from employee where Upper(Name) like upper(?1%)
	List<Employee> findByNameStartingWithIgnoreCase(String namePrefix);
	//select * from employee where startWork between ?1 and ?2
	List<Employee> findByStartWorkBetween(LocalDateTime from, LocalDateTime to);
	
	@Query("SELECT DISTINCT e FROM Employee e "
			+ "WHERE e.salary < :salary and e.position.name = :positionName")
	public List<Employee> findByPositionWithSalaryLowerThan(Integer salary, String positionName);
	
	@Query("SELECT DISTINCT e FROM Company c JOIN c.employees e "
			+ "WHERE e.salary < :salary and c.companyId = :companyId and e.position.name = :positionName")
	public List<Employee> findByCompanyAndPositionWithSalaryLowerThan(Integer salary, Long companyId, String positionName);
	
	@Modifying
	@Transactional
	@Query("UPDATE Employee e "
			+ "SET e.salary = :minSalary "
			+ "WHERE e.id IN(   "
			+ "SELECT e2.id FROM Employee e2 "
			+ "WHERE e2.position.name = :positionName "
			+ "AND e2.company.id=:companyId "
			+ "AND e2.salary < :minSalary)")
	void updateSalariesWithCompany(String positionName, int minSalary, long companyId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Employee e "
			+ "SET e.salary = :minSalary "
			+ "WHERE e.id IN(   "
			+ "SELECT e2.id FROM Employee e2 "
			+ "WHERE e2.position.name = :positionName "
			+ "AND e2.salary < :minSalary)")
	void updateSalaries(String positionName, int minSalary);
	
	@Query("SELECT distinct e FROM Employee e LEFT JOIN FETCH e.holidays h where e.employeeID = :id")
	@QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")},
            forCounting = false)
	public Employee findByIdWithHoliday(Long id);
	
	Optional<Employee> findByUsername(String username);
}

package hu.webuni.hr.gye.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Position;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	//select * from employee where position = ?1
	Page<Employee> findByPosition(Position position, Pageable page);
	//select * from employee where Upper(Name) like upper(?1%)
	List<Employee> findByNameStartingWithIgnoreCase(String namePrefix);
	//select * from employee where startWork between ?1 and ?2
	List<Employee> findByStartWorkBetween(LocalDateTime from, LocalDateTime to);
}

package hu.webuni.hr.gye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gye.model.PositionDetail;

public interface PositionDetailRepository extends JpaRepository<PositionDetail, Long>{

	@Query("SELECT DISTINCT pd FROM PositionDetail pd JOIN pd.position p "
			+ "WHERE pd.minSalary < :salary and p.name = :positionName")
	public List<PositionDetail> findByPositionWithSalaryLowerThan(Integer salary, String positionName);
	
	@Query("SELECT DISTINCT pd FROM PositionDetail pd JOIN pd.position p "
			+ "WHERE pd.minSalary < :salary and p.name = :positionName and pd.company.companyId = :companyId")
	public List<PositionDetail> findByPositionAndCompanyIdWithSalaryLowerThan(Integer salary, String positionName, Long companyId);
}

package hu.webuni.hr.gye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.PositionDetail;

public interface PositionDetailRepository extends JpaRepository<PositionDetail, Long>{

	@Query("SELECT DISTINCT pd FROM PositionDetail pd JOIN pd.position p "
			+ "WHERE pd.minSalary < :salary and p.name = :positionName")
	public List<PositionDetail> findByPositionWithSalaryLowerThan(Integer salary, String positionName);
	
	@Query("SELECT DISTINCT pd FROM PositionDetail pd JOIN pd.position p "
			+ "WHERE pd.minSalary < :salary and p.name = :positionName and pd.company.companyId = :companyId")
	public List<PositionDetail> findByPositionAndCompanyIdWithSalaryLowerThan(Integer salary, String positionName, Long companyId);


	@Modifying
	@Transactional
	@Query("UPDATE PositionDetail pd "
			+ "SET pd.minSalary = :minSalary "
			+ "WHERE pd.id IN(   "
			+ "SELECT pd2.id FROM PositionDetail pd2 "
			+ "WHERE pd2.position.name = :positionName "
			+ "AND pd2.company.companyId=:companyId)")
	void updatePositionMinSalaryWithCompany(String positionName, int minSalary, long companyId);
	
	@Modifying
	@Transactional
	@Query("UPDATE PositionDetail pd "
			+ "SET pd.minSalary = :minSalary "
			+ "WHERE pd.id IN(   "
			+ "SELECT pd2.id FROM PositionDetail pd2 "
			+ "WHERE pd2.position.name = :positionName )")
	void updatePositionMinSalary(String positionName, int minSalary);
}

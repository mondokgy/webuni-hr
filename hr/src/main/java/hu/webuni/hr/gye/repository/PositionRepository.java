package hu.webuni.hr.gye.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{

	Position findByName(String positionName);
	
}

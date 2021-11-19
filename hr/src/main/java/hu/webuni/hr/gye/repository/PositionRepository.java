package hu.webuni.hr.gye.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gye.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{

	Position findByName(String positionName);
}

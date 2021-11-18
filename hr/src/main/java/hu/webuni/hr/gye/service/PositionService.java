package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.PositionRepository;

@Service
public class PositionService {

private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	PositionRepository positionRepository;
	
	@Transactional
	public Position save(Position position) {
		log.debug("called Position.save()");
		return positionRepository.save(position);
	}
	
	public List<Position> findAll(){
		log.debug("called Position.findAll()");
		return positionRepository.findAll();
	}

	public Position findByName(String name){
		log.debug("called Position.findByName()");
		return positionRepository.findByName(name);
	}
	
	public Optional<Position> findById(Long id){
		log.debug("called Position.findById()");
		return positionRepository.findById(id);
	}
	
	@Transactional
	public Position delete(Long id) {
		log.debug("called Position.delete()");
		Position position = findById(id)
				.orElseThrow(()->new NoSuchElementException());
		positionRepository.deleteById(id);
		return position;
	}
	
}

package hu.webuni.hr.gye.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.repository.CompanyTypeRepository;

@Service
public class CompanyTypeService {

private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	@Transactional
	public CompanyType save(CompanyType companyType) {
		log.debug("called CompanyTypeService.save()");
		return companyTypeRepository.save(companyType);
	}
	
	@Transactional
	public List<CompanyType> findAll(){
		log.debug("called CompanyTypeService.findAll()");
		return companyTypeRepository.findAll();
	}

	@Transactional
	public Optional<CompanyType> findById(Long id){
		log.debug("called CompanyTypeService.findById()");
		return companyTypeRepository.findById(id);
	}
	
	@Transactional
	public CompanyType delete(Long id) {
		log.debug("called CompanyTypeService.delete()");
		CompanyType companyType = findById(id)
				.orElseThrow(()->new NoSuchElementException());
		companyTypeRepository.deleteById(id);
		return companyType;
	}
	
}

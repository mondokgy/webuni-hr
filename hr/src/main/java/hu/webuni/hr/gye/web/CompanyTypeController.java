package hu.webuni.hr.gye.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.CompanyTypeDto;
import hu.webuni.hr.gye.mapper.CompanyTypeMapper;
import hu.webuni.hr.gye.model.CompanyType;
import hu.webuni.hr.gye.service.CompanyTypeService;

@RestController
@RequestMapping("/api/companyTypes")
public class CompanyTypeController {
	private static final Logger log = LoggerFactory.getLogger("LOG");

	@Autowired
	private CompanyTypeService companyTypeService;
	
	@Autowired
	private CompanyTypeMapper companyTypeMapper;
	
	@GetMapping
	public List<CompanyTypeDto> getAll(){


		log.debug("restapi controller Address, /, get, getAll start");
		List<CompanyTypeDto> allCompanyType = companyTypeMapper.companyTypeToDto(companyTypeService.findAll());
		
		return allCompanyType;
			
	}
	
	@PostMapping
	public CompanyTypeDto createCompanyType(@Valid @RequestBody CompanyTypeDto companyTypeDto) {
		
		log.debug("restapi controller CompanyType, /, post, createCompanyType start");		
		CompanyType companyType = companyTypeService.save(companyTypeMapper.dtoToCompanyType(companyTypeDto));
		log.debug("restapi controller CompanyType, /, post, createCompanyType end");
		
		return  companyTypeMapper.companyTypeToDto(companyType);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyTypeDto> deleteCompanyType(@PathVariable Long id) {
		
		log.debug("restapi controller CompanyType, /{id}, delete, deleteCompanyType start");
		try {
			CompanyTypeDto companyTypeDto = companyTypeMapper.companyTypeToDto(companyTypeService.delete(id));
			log.debug("restapi controller, /{id}, delete, deleteCompanyType end");
			return ResponseEntity.ok(companyTypeDto);
		}catch (NoSuchElementException e) {
			log.debug("Invalid input: companyTypeDto.");
			log.debug("restapi controller CompanyType, /{id}, delete, deleteCompanyType end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
	}
}

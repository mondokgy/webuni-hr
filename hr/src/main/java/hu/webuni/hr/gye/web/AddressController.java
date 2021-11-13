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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gye.dto.AddressDto;
import hu.webuni.hr.gye.mapper.AddressMapper;
import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressMapper addressMapper;
	
	@GetMapping
	public List<AddressDto> getAll(){
		
		log.debug("restapi controller Address, /, get, getAll start");
		List<AddressDto> allAddress = addressMapper.addressToDto(addressService.findAll());
		
		return allAddress;
			
	}
	
	@GetMapping("/{id}")
	public AddressDto getById(@PathVariable Long id){
		
		log.debug("restapi controller Address, /{id}, get, getById start");
		
		Address address = addressService.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

		return addressMapper.addressToDto(address);
	}
	
	@PostMapping
	public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto) {
		
		log.debug("restapi controller Address, /, post, createAddress start");		
		Address address = addressService.save(addressMapper.dtoToAddress(addressDto));
		log.debug("restapi controller Address, /, post, createAddress end");
		
		return  addressMapper.addressToDto(address);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> modifyAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto) {
		
		log.debug("restapi controller Address, /{id}, put, modifyAddress start");
		try {
			addressDto = addressMapper.addressToDto(addressService.modify(id, addressMapper.dtoToAddress(addressDto)));
			log.debug("restapi controller, /{id}, put, modifyAddress end");		
			return ResponseEntity.ok(addressDto);
		}catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long id) {
		
		log.debug("restapi controller Address, /{id}, delete, deleteAddress start");
		try {
			AddressDto addressDto = addressMapper.addressToDto(addressService.delete(id));
			log.debug("restapi controller, /{id}, delete, deleteAddress end");
			return ResponseEntity.ok(addressDto);
		}catch (NoSuchElementException e) {
			log.debug("Invalid input: addressDto.");
			log.debug("restapi controller Address, /{id}, get, getById end");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
	}
	

}

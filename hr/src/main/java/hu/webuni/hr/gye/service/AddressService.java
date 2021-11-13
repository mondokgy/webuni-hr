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
import hu.webuni.hr.gye.repository.AddressRepository;

@Service
public class AddressService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	AddressRepository addressRepository;
	
	@Transactional
	public Address save(Address address) {
		log.debug("called AddressService.save()");
		return addressRepository.save(address);
	}
	
	@Transactional
	public List<Address> findAll(){
		log.debug("called AddressService.findAll()");
		return addressRepository.findAll();
	}
	
	@Transactional
	public Optional<Address> findById(Long id){
		log.debug("called AddressService.findById()");
		return addressRepository.findById(id);
	}
	
	@Transactional
	public Address delete(Long id) {
		log.debug("called AddressService.delete()");
		Address address = findById(id)
				.orElseThrow(()->new NoSuchElementException());
		addressRepository.deleteById(id);
		return address;
	}
	
	@Transactional
	public Address modify(Long id, Address changedAddress) {

		log.debug("called AddressService.modify()");
		findById(id).orElseThrow(()->new NoSuchElementException());

		changedAddress.setAddressId(id);
		
		return addressRepository.save(changedAddress);

	}
		
}

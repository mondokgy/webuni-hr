package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.gye.dto.AddressDto;
import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Company;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	List<AddressDto> addressToDto(List<Address> addresses);
	List<Address> dtoToAddresses(List<AddressDto> addressesDto);
	AddressDto addressToDto(Address address);
	Address dtoToAddress(AddressDto addressDto);
	
	@Mapping(target = "addresses", ignore = true)
	@Mapping(target = "employees", ignore = true)
	CompanyDto companyToDto(Company c);

}

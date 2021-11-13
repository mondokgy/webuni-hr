package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.gye.dto.AddressDto;
import hu.webuni.hr.gye.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	List<AddressDto> addressToDto(List<Address> addresses);
	List<Address> dtoToAddresses(List<AddressDto> addressesDto);
	AddressDto addressToDto(Address address);
	Address dtoToAddress(AddressDto addressDto);
}

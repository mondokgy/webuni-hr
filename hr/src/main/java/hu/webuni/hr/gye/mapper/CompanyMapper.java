package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gye.dto.AddressDto;
import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.model.Address;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	List<CompanyDto> companiesToDto(List<Company> companies);
	CompanyDto companyToDto(Company company);
	Company dtoToCompany(CompanyDto companyDto);
	
	@Mapping(target="employees", ignore=true)
	@Mapping(target="addresses", ignore=true)
	@Named("summary")
	CompanyDto companyToDtoWithOutEmployee(Company company);
	
	@IterableMapping(qualifiedByName="summary")
	List<CompanyDto> companiesToDtoWithOutEmployee(List<Company> companies);

	@Mapping(target = "company", ignore = true)
	EmployeeDto employeeToDto(Employee e);

	@Mapping(target = "company", ignore = true)
	AddressDto addressToDto(Address a);
}

package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	List<CompanyDto> companiesToDto(List<Company> companies);
	CompanyDto companyToDto(Company company);
	Company dtoToCompany(CompanyDto companyDto);
	@Mapping(target="employees", ignore=true)
	@Named("summary")
	CompanyDto companyToDtoWithOutEmployee(Company company);
	
	@IterableMapping(qualifiedByName="summary")
	List<CompanyDto> companiesToDtoWithOutEmployee(List<Company> companies);
	
}

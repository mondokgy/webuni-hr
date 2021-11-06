package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	List<CompanyDto> companiesToDto(List<Company> companies);
	CompanyDto companyToDto(Company company);
	Company dtoToCompany(CompanyDto companyDto);
}

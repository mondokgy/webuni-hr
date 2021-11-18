package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.gye.dto.CompanyTypeDto;
import hu.webuni.hr.gye.model.CompanyType;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {
	List<CompanyTypeDto> companyTypeToDto(List<CompanyType> companyType);
	List<CompanyType> dtoToCompanyType(List<CompanyTypeDto> companyTypeDto);
	CompanyTypeDto companyTypeToDto(CompanyType companyType);
	CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);
}

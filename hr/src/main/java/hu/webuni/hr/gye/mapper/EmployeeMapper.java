package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	List<EmployeeDto> employeesToDto(List<Employee> employees);
	List<Employee> dtoToEmployees(List<EmployeeDto> employeeDto);
	
	EmployeeDto employeeToDto(Employee employees);
	
	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
	@Mapping(target = "employees", ignore = true)
	@Mapping(target = "addresses", ignore = true)
	CompanyDto companyToDto(Company c);

}

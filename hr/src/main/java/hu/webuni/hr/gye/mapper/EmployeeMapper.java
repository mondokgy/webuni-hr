package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	List<EmployeeDto> employeesToDto(List<Employee> employees);
	List<Employee> dtoToEmployees(List<EmployeeDto> employeeDto);
	EmployeeDto employeeToDto(Employee employees);
	Employee dtoToEmployee(EmployeeDto employeeDto);

}

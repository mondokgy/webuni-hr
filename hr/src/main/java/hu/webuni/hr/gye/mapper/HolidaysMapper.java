package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.gye.dto.EmployeeDto;
import hu.webuni.hr.gye.dto.HolidaysDto;
import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.model.Holidays;

@Mapper(componentModel = "spring")
public interface HolidaysMapper {
	List<HolidaysDto> holidaysToDtos(List<Holidays> holidays);
	HolidaysDto holidayToDto(Holidays holidays);
	Holidays dtoToHoliday(HolidaysDto holidaysDto);
	
	@Mapping(target = "company", ignore = true)
	EmployeeDto employeeToDto(Employee e);

}

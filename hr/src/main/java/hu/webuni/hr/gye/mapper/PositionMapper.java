package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gye.dto.CompanyDto;
import hu.webuni.hr.gye.dto.PositionDetailDto;
import hu.webuni.hr.gye.dto.PositionDto;
import hu.webuni.hr.gye.model.Company;
import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.model.PositionDetail;

@Mapper(componentModel = "spring")
public interface PositionMapper {
	List<PositionDto> positionToDto(List<Position> position);
	List<Position> dtoToCompanyType(List<PositionDto> positionDto);
	PositionDto positionToDto(Position position);
	Position dtoToPosition(PositionDto positionDto);
	
	@Mapping(target="company", ignore=true)
	@Named("summary")
	PositionDetailDto positionDetailToDto(PositionDetail positionDetail);
	
	@IterableMapping(qualifiedByName="summary")
	List<PositionDetailDto> positionDetailsToDto(List<PositionDetail> positionDetails);
}

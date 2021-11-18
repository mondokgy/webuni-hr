package hu.webuni.hr.gye.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.gye.dto.PositionDto;
import hu.webuni.hr.gye.model.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {
	List<PositionDto> positionToDto(List<Position> position);
	List<Position> dtoToCompanyType(List<PositionDto> positionDto);
	PositionDto positionToDto(Position position);
	Position dtoToPosition(PositionDto positionDto);
}

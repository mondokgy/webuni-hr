package hu.webuni.hr.gye.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import hu.webuni.hr.gye.model.Position;
import hu.webuni.hr.gye.repository.PositionRepository;

class PositionEmptyValidator implements ConstraintValidator<NotEmptyPosition, Position> {

    private PositionRepository positionRepository;

    public PositionEmptyValidator(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public void initialize(NotEmptyPosition constraint) {
    }

    public boolean isValid(Position position, ConstraintValidatorContext context) {
    	boolean retVal = position.getName()==null;
    	retVal = !(retVal || position.getName().isEmpty());
    	Position p = positionRepository.findByName(position.getName());
    	if(retVal && p==null) {
    		retVal = false;
    	}
        return (retVal);
    }

}


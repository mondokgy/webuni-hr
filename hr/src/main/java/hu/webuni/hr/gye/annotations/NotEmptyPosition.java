package hu.webuni.hr.gye.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositionEmptyValidator.class)
public @interface NotEmptyPosition {
    String message() default "Empty position!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

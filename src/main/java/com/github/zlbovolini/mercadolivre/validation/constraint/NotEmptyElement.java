package com.github.zlbovolini.mercadolivre.validation.constraint;

import com.github.zlbovolini.mercadolivre.validation.validator.NotEmptyElementValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyElementValidator.class)
public @interface NotEmptyElement {

    String message() default "{com.github.zlbovolini.constraints.NotEmptyElement}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

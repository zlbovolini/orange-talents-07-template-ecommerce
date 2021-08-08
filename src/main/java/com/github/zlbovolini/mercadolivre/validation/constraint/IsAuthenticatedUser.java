package com.github.zlbovolini.mercadolivre.validation.constraint;

import com.github.zlbovolini.mercadolivre.validation.validator.IsAuthenticatedUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = IsAuthenticatedUserValidator.class)
public @interface IsAuthenticatedUser {

    String message() default "{com.github.zlbovolini.constraints.IsAuthenticatedUser}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.github.zlbovolini.mercadolivre.validation.constraint;

import com.github.zlbovolini.mercadolivre.validation.validator.IsOwnerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = IsOwnerValidator.class)
public @interface IsOwner {

    String message() default "{com.github.zlbovolini.constraints.IsOwner}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String owner() default "owner";

    String ownerId() default "id";
}

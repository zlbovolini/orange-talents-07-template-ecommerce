package com.github.zlbovolini.mercadolivre.validation.constraint;

import com.github.zlbovolini.mercadolivre.validation.validator.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {

    String message() default "{com.github.zlbovolini.constraints.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field() default "id";

    /**
     * Este parametro define se a regra é válida caso um valor nulo seja informado.
     * @return
     */
    boolean orNull() default false;
}

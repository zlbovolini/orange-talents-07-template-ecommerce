package com.github.zlbovolini.mercadolivre.validation.validator;

import com.github.zlbovolini.mercadolivre.validation.constraint.Exists;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    private Class<?> entity;
    private String field;
    private boolean orNull;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Exists constraintAnnotation) {
        entity = constraintAnnotation.entity();
        field = constraintAnnotation.field();
        orNull = constraintAnnotation.orNull();
    }

    @Override
    public boolean isValid(Object fieldValue, ConstraintValidatorContext constraintValidatorContext) {

        if (orNull && Objects.isNull(fieldValue)) {
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder()
                .append("SELECT COUNT(e) > 0 FROM ")
                .append(entity.getName())
                .append(" e WHERE e.")
                .append(field)
                .append(" = :fieldValue");

        TypedQuery<Boolean> query = entityManager.createQuery(stringBuilder.toString(), Boolean.class);
        query.setParameter("fieldValue", fieldValue);

        return query.getSingleResult();
    }
}

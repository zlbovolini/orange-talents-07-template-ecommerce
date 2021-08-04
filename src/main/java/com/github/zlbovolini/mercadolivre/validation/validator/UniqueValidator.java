package com.github.zlbovolini.mercadolivre.validation.validator;

import com.github.zlbovolini.mercadolivre.validation.constraint.Unique;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private Class<?> entity;
    private String field;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        entity = constraintAnnotation.entity();
        field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object fieldValue, final ConstraintValidatorContext context) {

        StringBuilder stringBuilder = new StringBuilder()
                .append("SELECT COUNT(e) = 0 FROM ")
                .append(entity.getName())
                .append(" e WHERE e.")
                .append(field)
                .append(" = :fieldValue");

        TypedQuery<Boolean> query = entityManager.createQuery(stringBuilder.toString(), Boolean.class);
        query.setParameter("fieldValue", fieldValue);

        return query.getSingleResult();
    }
}

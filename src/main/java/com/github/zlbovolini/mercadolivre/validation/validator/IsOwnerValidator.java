package com.github.zlbovolini.mercadolivre.validation.validator;

import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.validation.constraint.IsOwner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class IsOwnerValidator implements ConstraintValidator<IsOwner, Long> {

    private Class<?> entity;
    private String owner;
    private String ownerId;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(IsOwner constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.owner = constraintAnnotation.owner();
        this.ownerId = constraintAnnotation.ownerId();
    }

    @Override
    public boolean isValid(final Long productId, ConstraintValidatorContext constraintValidatorContext) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(productId) || Objects.isNull(authentication)) {
            return false;
        }

        final StringBuilder sb = new StringBuilder()
                .append("SELECT count(id) > 0 FROM ")
                .append(entity.getName())
                .append(" WHERE ")
                .append(" id = :productId AND ")
                .append(owner)
                .append(".")
                .append(ownerId)
                .append(" = :ownerId");

        return Optional.ofNullable((AuthenticatedUser) authentication.getPrincipal())
                .map(authenticatedUser -> {
                    TypedQuery<Boolean> query = entityManager.createQuery(sb.toString(), Boolean.class);
                    query.setParameter("productId", productId);
                    query.setParameter("ownerId", authenticatedUser.getId());

                    return query.getSingleResult();
                })
                .orElse(false);
    }
}

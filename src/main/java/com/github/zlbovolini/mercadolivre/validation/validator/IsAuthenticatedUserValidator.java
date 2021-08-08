package com.github.zlbovolini.mercadolivre.validation.validator;

import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.validation.constraint.IsAuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class IsAuthenticatedUserValidator implements ConstraintValidator<IsAuthenticatedUser, Long> {

    @Override
    public void initialize(IsAuthenticatedUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long ownerId, ConstraintValidatorContext constraintValidatorContext) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(ownerId) || Objects.isNull(authentication)) {
            return false;
        }

        return Optional.ofNullable((AuthenticatedUser) authentication.getPrincipal())
                .map(authenticatedUser -> ownerId.equals(authenticatedUser.getId()))
                .orElse(false);
    }
}

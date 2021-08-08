package com.github.zlbovolini.mercadolivre.validation.validator;

import com.github.zlbovolini.mercadolivre.validation.constraint.NotEmptyElement;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Objects;

public class NotEmptyElementValidator implements ConstraintValidator<NotEmptyElement, Collection<MultipartFile>> {

    private String message;

    @Override
    public void initialize(NotEmptyElement constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Collection<MultipartFile> collection, ConstraintValidatorContext context) {

        if (Objects.isNull(collection) || collection.isEmpty()) {
            return false;
        }

        return collection.stream().noneMatch(e -> Objects.isNull(e) || e.isEmpty());
    }
}

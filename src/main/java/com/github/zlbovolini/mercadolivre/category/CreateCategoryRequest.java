package com.github.zlbovolini.mercadolivre.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.zlbovolini.mercadolivre.validation.constraint.Exists;
import com.github.zlbovolini.mercadolivre.validation.constraint.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class CreateCategoryRequest {

    @NotBlank
    @Unique(entity = Category.class, field = "name")
    private final String name;

    @Exists(entity = Category.class, orNull = true)
    private Long superCategoryId;

    @JsonCreator(mode = PROPERTIES)
    CreateCategoryRequest(String name, Long superCategoryId) {
        this.name = name;
        this.superCategoryId = superCategoryId;
    }

    Category toModel(@NotNull BiFunction<Class<?>, Object, ?> find) {

        if (Objects.isNull(superCategoryId)) {
            return new Category(name);
        }

        Category superCategory = Optional.ofNullable((Category) find.apply(Category.class, superCategoryId))
                .orElseThrow();

        return new Category(name, superCategory);
    }
}

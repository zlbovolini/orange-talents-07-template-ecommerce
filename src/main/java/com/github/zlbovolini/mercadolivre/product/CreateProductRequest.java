package com.github.zlbovolini.mercadolivre.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.zlbovolini.mercadolivre.category.Category;
import com.github.zlbovolini.mercadolivre.user.User;
import com.github.zlbovolini.mercadolivre.validation.constraint.Exists;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class CreateProductRequest {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final BigDecimal price;

    @NotNull
    @PositiveOrZero
    private final Long quantity;

    @NotBlank
    @Size(max = 1000)
    private final String description;

    @NotNull
    @Exists(entity = Category.class)
    private final Long categoryId;

    @Valid
    @NotNull
    @Size(min = 3)
    @UniqueElements
    private final List<CreateProductCharacteristicRequest> characteristics;

    @JsonCreator(mode = PROPERTIES)
    CreateProductRequest(String name, BigDecimal price, Long quantity, String description, Long categoryId,
                         List<CreateProductCharacteristicRequest> characteristics) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.characteristics = characteristics;
    }

    public List<CreateProductCharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    Product toModel(@NotNull final BiFunction<Class<?>, Object, ?> find, User owner) {
        Category category = Optional.ofNullable((Category) find.apply(Category.class, categoryId))
                .orElseThrow();

        return new Product(name, price, quantity, description, owner, category, characteristics);
    }
}

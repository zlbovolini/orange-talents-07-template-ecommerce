package com.github.zlbovolini.mercadolivre.product;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class CreateProductCharacteristicRequest {

    @NotBlank
    private final String name;

    @NotBlank
    private final String description;

    @JsonCreator(mode = PROPERTIES)
    CreateProductCharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    ProductCharacteristic toModel(Product product) {
        return new ProductCharacteristic(name, description, product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateProductCharacteristicRequest that = (CreateProductCharacteristicRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

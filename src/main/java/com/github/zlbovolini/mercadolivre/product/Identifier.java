package com.github.zlbovolini.mercadolivre.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.zlbovolini.mercadolivre.validation.constraint.IsOwner;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class Identifier implements Serializable {

    @NotNull
    @IsOwner(entity = Product.class)
    private final Long id;

    @JsonCreator(mode = PROPERTIES)
    public Identifier(@PathVariable Long id) {
        this.id = id;
    }

    Long getId() {
        return id;
    }
}

package com.github.zlbovolini.mercadolivre.addproductquestion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

public class CreateProductQuestionRequest {

    @NotBlank
    private final String title;

    @JsonCreator(mode = PROPERTIES)
    CreateProductQuestionRequest(String title) {
        this.title = title;
    }

    public ProductQuestion toModel(@NotNull Product product, @NotNull User user) {
        return new ProductQuestion(title, product, user);
    }

    String getTitle() {
        return title;
    }
}

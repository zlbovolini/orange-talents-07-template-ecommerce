package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.user.User;

import javax.validation.constraints.*;

class CreateProductOpinionRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private final Integer note;

    @NotBlank
    private final String title;

    @NotBlank
    @Size(max = 500)
    private final String description;

    CreateProductOpinionRequest(Integer note, String title, String description) {
        this.note = note;
        this.title = title;
        this.description = description;
    }

    ProductOpinion toModel(Product product, User user) {
        return new ProductOpinion(note, title, description, product, user);
    }
}

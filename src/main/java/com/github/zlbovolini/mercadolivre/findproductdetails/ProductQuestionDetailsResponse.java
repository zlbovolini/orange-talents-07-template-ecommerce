package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.addproductquestion.ProductQuestion;

import java.time.Instant;

class ProductQuestionDetailsResponse {

    private final Long id;

    private final String title;

    private final Instant createdAt;

    private final UserDetailsResponse user;

    ProductQuestionDetailsResponse(ProductQuestion question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.createdAt = question.getCreatedAt();
        this.user = new UserDetailsResponse(question.getUser());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserDetailsResponse getUser() {
        return user;
    }
}

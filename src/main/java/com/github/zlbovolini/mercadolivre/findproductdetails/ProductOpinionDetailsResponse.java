package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.product.ProductOpinion;

class ProductOpinionDetailsResponse {

    private final Long id;

    private final Integer rate;

    private final String title;

    private final String description;

    private final UserDetailsResponse user;

    ProductOpinionDetailsResponse(ProductOpinion opinion) {
        this.id = opinion.getId();
        this.rate = opinion.getRate();
        this.title = opinion.getTitle();
        this.description = opinion.getDescription();
        this.user = new UserDetailsResponse(opinion.getUser());
    }

    public Long getId() {
        return id;
    }

    public Integer getRate() {
        return rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserDetailsResponse getUser() {
        return user;
    }
}

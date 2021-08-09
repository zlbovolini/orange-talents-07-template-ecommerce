package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.category.Category;

class CategoryDetailsResponse {

    private final Long id;

    private final String name;

    CategoryDetailsResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

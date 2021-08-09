package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.product.ProductImage;

class ProductImageDetailsResponse {

    private final String uri;

    ProductImageDetailsResponse(ProductImage productImage) {
        this.uri = productImage.getUri();
    }

    public String getUri() {
        return uri;
    }
}

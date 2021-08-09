package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.product.ProductCharacteristic;

class ProductCharacteristicDetailsResponse {

    private final Long id;

    private final String name;

    private final String description;

    ProductCharacteristicDetailsResponse(ProductCharacteristic characteristic) {
        this.id = characteristic.getId();
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

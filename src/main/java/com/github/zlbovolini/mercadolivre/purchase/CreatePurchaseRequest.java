package com.github.zlbovolini.mercadolivre.purchase;

import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.user.User;
import com.github.zlbovolini.mercadolivre.validation.constraint.Exists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreatePurchaseRequest {

    @NotNull
    @Exists(entity = Product.class)
    private final Long productId;

    @NotNull
    @Positive
    private final Integer quantity;

    @NotNull
    private final PaymentGatewayService gateway;

    CreatePurchaseRequest(Long productId, Integer quantity, PaymentGatewayService gateway) {
        this.productId = productId;
        this.quantity = quantity;
        this.gateway = gateway;
    }

    Long getProductId() {
        return productId;
    }

    Integer getQuantity() {
        return quantity;
    }

    PaymentGatewayService getGateway() {
        return gateway;
    }

    public Purchase toModel(@NotNull Product product, @NotNull User buyer) {
        return new Purchase(gateway, product, quantity, buyer);
    }
}

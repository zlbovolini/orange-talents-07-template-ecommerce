package com.github.zlbovolini.mercadolivre.purchase;

import java.net.URI;
import java.util.UUID;

enum PaymentGatewayService {

    PAYPAL(new PayPalPaymentGateway()),

    PAGSEGURO(new PagSeguroPaymentGateway());

    private final PaymentGateway paymentGateway;

    PaymentGatewayService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public URI pay(UUID uuid) {
        return paymentGateway.pay(uuid);
    }

}

package com.github.zlbovolini.mercadolivre.purchase;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

class PayPalPaymentGateway implements PaymentGateway {

    private final String callback = "/api/v1/payment/paypal/{purchaseIdentifier}";

    @Override
    public URI pay(@NotNull UUID purchaseIdentifier) {
        UriComponentsBuilder context = ServletUriComponentsBuilder.fromCurrentContextPath();

        return UriComponentsBuilder.fromHttpUrl("https://paypal.com?buyerId={purchaseIdentifier}&redirectUrl={callback}")
                .buildAndExpand(Map.of("purchaseIdentifier", purchaseIdentifier,
                        "callback", context.path(callback).buildAndExpand(Map.of("purchaseIdentifier", purchaseIdentifier))))
                .toUri();
    }
}

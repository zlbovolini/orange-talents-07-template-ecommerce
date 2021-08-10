package com.github.zlbovolini.mercadolivre.purchase;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

interface PaymentGateway {

    URI pay(@NotNull UUID purchaseIdentifier);
}

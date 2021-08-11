package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;

import javax.validation.constraints.NotNull;

public interface GatewayResponseRequest {

    Transaction toTransaction(@NotNull Purchase purchase);
}

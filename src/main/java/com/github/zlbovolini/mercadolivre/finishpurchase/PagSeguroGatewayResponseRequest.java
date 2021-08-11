package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagSeguroGatewayResponseRequest implements GatewayResponseRequest {

    @NotBlank
    private final String transactionId;

    @NotNull
    private final PagSeguroTransactionStatus status;

    public PagSeguroGatewayResponseRequest(String transactionId, PagSeguroTransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public Transaction toTransaction(@NotNull Purchase purchase) {
        return new Transaction(transactionId, status.normalize(), purchase);
    }
}

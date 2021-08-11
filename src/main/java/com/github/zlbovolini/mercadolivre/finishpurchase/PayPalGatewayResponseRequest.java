package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayPalGatewayResponseRequest implements GatewayResponseRequest {

    @NotBlank
    private final String transactionId;

    @NotNull
    @Min(0)
    @Max(1)
    private final Integer status;

    public PayPalGatewayResponseRequest(String transactionId, Integer status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public Transaction toTransaction(@NotNull Purchase purchase) {
        TransactionStatus transactionStatus = status == 0 ? TransactionStatus.ERROR : TransactionStatus.SUCCESS;
        return new Transaction(transactionId, transactionStatus, purchase);
    }
}

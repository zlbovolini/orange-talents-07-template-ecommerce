package com.github.zlbovolini.mercadolivre.finishpurchase;

public enum PagSeguroTransactionStatus {

    ERROR,
    SUCCESS;

    public TransactionStatus normalize() {
        if (this.equals(ERROR)) {
            return TransactionStatus.ERROR;
        }

        return TransactionStatus.SUCCESS;
    }
}

package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;

public interface SuccessPurchaseEvent {

    void execute(Purchase purchase);
}

package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NewPurchaseEvents {

    private final Set<SuccessPurchaseEvent> successPurchaseEvents;

    public NewPurchaseEvents(Set<SuccessPurchaseEvent> successPurchaseEvents) {
        this.successPurchaseEvents = successPurchaseEvents;
    }

    public void execute(Purchase purchase) {

        if (purchase.isCompletedWithSuccess()) {
            successPurchaseEvents.forEach(event -> {
                event.execute(purchase);
            });
        }
    }
}

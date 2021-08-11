package com.github.zlbovolini.mercadolivre.email;

import com.github.zlbovolini.mercadolivre.finishpurchase.SuccessPurchaseEvent;

public interface SendEmailService extends SuccessPurchaseEvent {

    void send(String email, String message);
}

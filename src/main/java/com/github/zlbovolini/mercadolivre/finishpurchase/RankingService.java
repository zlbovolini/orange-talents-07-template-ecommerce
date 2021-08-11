package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;
import org.springframework.stereotype.Service;

@Service
public class RankingService implements SuccessPurchaseEvent {

    @Override
    public void execute(Purchase purchase) {
        System.out.println("Atualizando ranking");
    }
}

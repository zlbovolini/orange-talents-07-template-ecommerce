package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
public class FinishPurchaseController {

    @PersistenceContext
    private final EntityManager entityManager;
    private final NewPurchaseEvents newPurchaseEvents;

    public FinishPurchaseController(EntityManager entityManager, NewPurchaseEvents newPurchaseEvents) {
        this.entityManager = entityManager;
        this.newPurchaseEvents = newPurchaseEvents;
    }

    @Transactional
    @PostMapping("/paypal/{purchaseIdentifier}")
    public ResponseEntity<Void> finishPurchaseWithPaypal(@PathVariable UUID purchaseIdentifier,
                                                         @Valid PayPalGatewayResponseRequest gatewayResponseRequest) {
        return finishPurchase(purchaseIdentifier, gatewayResponseRequest);
    }

    @Transactional
    @PostMapping("/pagseguro/{purchaseIdentifier}")
    public ResponseEntity<Void> finishPurchaseWithPagSeguro(@PathVariable UUID purchaseIdentifier,
                                                            @Valid PagSeguroGatewayResponseRequest gatewayResponseRequest) {
        return finishPurchase(purchaseIdentifier, gatewayResponseRequest);
    }

    private ResponseEntity<Void> finishPurchase(UUID purchaseIdentifier, GatewayResponseRequest gatewayResponseRequest) {

        TypedQuery<Purchase> query = entityManager.createQuery("SELECT p FROM Purchase p WHERE p.uuid  = CAST( :uuid as org.hibernate.type.UUIDBinaryType)", Purchase.class);
        query.setParameter("uuid", purchaseIdentifier);

        Purchase purchase = query.getSingleResult();

        purchase.addTransaction(gatewayResponseRequest);

        entityManager.merge(purchase);
        newPurchaseEvents.execute(purchase);

        return ResponseEntity.ok().build();
    }
}

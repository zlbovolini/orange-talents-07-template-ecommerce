package com.github.zlbovolini.mercadolivre.purchase;

import com.github.zlbovolini.mercadolivre.email.SendEmailService;
import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/purchases")
public class CreatePurchaseController {

    @PersistenceContext
    private final EntityManager entityManager;
    private final SendEmailService sendEmailService;

    public CreatePurchaseController(EntityManager entityManager, SendEmailService sendEmailService) {
        this.entityManager = entityManager;
        this.sendEmailService = sendEmailService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@Valid @RequestBody CreatePurchaseRequest purchaseRequest,
                               @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        Product product = Optional.ofNullable(entityManager.find(Product.class, purchaseRequest.getProductId()))
                .orElseThrow();

        boolean canBuy = product.buy(purchaseRequest.getQuantity());

        if (!canBuy) {
            return ResponseEntity.badRequest().build();
        }

        User buyer = entityManager.find(User.class, authenticatedUser.getId());
        Purchase purchase = purchaseRequest.toModel(product, buyer);

        entityManager.persist(purchase);

        String productOwnerEmail = product.getOwnerEmail();
        String message = format("Nova compra para o produto %s com %d quantidades realizada por %s",
                product.getName(), purchaseRequest.getQuantity(), buyer.getEmail());

        sendEmailService.send(productOwnerEmail, message);

        PaymentGatewayService gateway = purchaseRequest.getGateway();
        URI redirect = gateway.pay(purchase.getUuid());

        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).location(redirect).build();
    }
}

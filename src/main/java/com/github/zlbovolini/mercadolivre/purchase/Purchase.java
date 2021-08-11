package com.github.zlbovolini.mercadolivre.purchase;

import com.github.zlbovolini.mercadolivre.finishpurchase.GatewayResponseRequest;
import com.github.zlbovolini.mercadolivre.finishpurchase.Transaction;
import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.zlbovolini.mercadolivre.purchase.PurchaseStatus.STARTED;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private PurchaseStatus status = STARTED;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentGatewayService gateway;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User buyer;

    @NotNull
    private BigDecimal priceOnPurchaseDate;

    private Instant purchasedAt = Instant.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    Purchase() {}

    public Purchase(PaymentGatewayService gateway, Product product, Integer quantity, User buyer) {
        this.gateway = gateway;
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.priceOnPurchaseDate = product.getPrice();
    }

    UUID getUuid() {
        return uuid;
    }

    public void addTransaction(GatewayResponseRequest request) {

        Transaction transaction = request.toTransaction(this);

        Assert.isTrue(!transactions.contains(transaction), "Transação já processada");
        boolean hasTransactionCompletedWithSuccess = this.transactions.stream().anyMatch(Transaction::completedWithSuccess);

        Assert.isTrue(!hasTransactionCompletedWithSuccess, "Compra já concluída");
        this.transactions.add(transaction);
    }

    public boolean isCompletedWithSuccess() {
        return this.transactions.stream().anyMatch(Transaction::completedWithSuccess);
    }
}

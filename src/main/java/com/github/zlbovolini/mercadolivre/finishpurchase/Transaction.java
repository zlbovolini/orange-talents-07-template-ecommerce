package com.github.zlbovolini.mercadolivre.finishpurchase;

import com.github.zlbovolini.mercadolivre.purchase.Purchase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String identifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Instant createdAt = Instant.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Purchase purchase;

    @Deprecated
    Transaction() {}

    public Transaction(String identifier, TransactionStatus status, Purchase purchase) {
        this.identifier = identifier;
        this.status = status;
        this.purchase = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Transaction that = (Transaction) o;

        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public boolean completedWithSuccess() {
        return status.equals(TransactionStatus.SUCCESS);
    }
}

package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ProductOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer note;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Deprecated
    ProductOpinion() {}

    ProductOpinion(Integer note, String title, String description, Product product, User user) {
        this.note = note;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Integer getRate() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }
}

package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.category.Category;
import com.github.zlbovolini.mercadolivre.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Long quantity;

    @NotBlank
    @Size(max = 1000)
    private String description;

    private Instant createdAt = Instant.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<ProductCharacteristic> characteristics;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductOpinion> opinions = new ArrayList<>();

    @Deprecated
    Product() {}

    Product(String name, BigDecimal price, Long quantity, String description, User owner, Category category, Collection<CreateProductCharacteristicRequest> characteristics) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.owner = owner;
        this.category = category;
        this.characteristics = characteristics.stream()
                .map(characteristic -> characteristic.toModel(this))
                .collect(Collectors.toSet());
    }

    void addProductImages(@NotNull @Size(min = 1) List<Resource> images) {
        this.images.addAll(images.stream()
                .map(image -> new ProductImage(image, this))
                .collect(Collectors.toList()));
    }

    public void addOpinion(CreateProductOpinionRequest opinion, User user) {
        this.opinions.add(opinion.toModel(this, user));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;

        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

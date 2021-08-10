package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.addproductquestion.CreateProductQuestionRequest;
import com.github.zlbovolini.mercadolivre.addproductquestion.ProductQuestion;
import com.github.zlbovolini.mercadolivre.category.Category;
import com.github.zlbovolini.mercadolivre.purchase.CreatePurchaseRequest;
import com.github.zlbovolini.mercadolivre.purchase.Purchase;
import com.github.zlbovolini.mercadolivre.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductQuestion> questions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.MERGE)
    private List<Purchase> purchases = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getOwnerEmail() {
        return owner.getEmail();
    }

    public Category getCategory() {
        return category;
    }

    void addProductImages(@NotNull @Size(min = 1) List<Resource> images) {
        this.images.addAll(images.stream()
                .map(image -> new ProductImage(image, this))
                .collect(Collectors.toList()));
    }

    public void addOpinion(CreateProductOpinionRequest opinion, User user) {
        this.opinions.add(opinion.toModel(this, user));
    }

    public void addQuestion(CreateProductQuestionRequest question, User user) {
        this.questions.add(question.toModel(this, user));
    }

    public void addPurchase(CreatePurchaseRequest purchase, User user) {
        this.purchases.add(purchase.toModel(this, user));
    }

    public <T> Set<T> mapCharacteristics(Function<ProductCharacteristic, T> mapper) {
        return this.characteristics.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public <T> List<T> mapImages(Function<ProductImage, T> mapper) {
        return this.images.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public <T> List<T> mapOpinions(Function<ProductOpinion, T> mapper) {
        return this.opinions.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public <T> List<T> mapQuestions(Function<ProductQuestion, T> mapper) {
        return this.questions.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    public boolean buy(@NotNull @Positive Integer quantity) {

        Assert.isTrue(quantity > 0, "A quantidade de produtos comprados deve ser maior que zero");

        if (this.quantity < quantity) {
            return false;
        }

        this.quantity -= quantity;

        return true;
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

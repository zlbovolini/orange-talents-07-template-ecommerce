package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.product.ProductOpinion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

class ProductDetailsResponse {

    private final Long id;

    private final String name;

    private final BigDecimal price;

    private final Long quantity;

    private final String description;

    private final Instant createdAt;

    private final Double averageRate;

    private final Integer rateAmount;

    private final CategoryDetailsResponse category;

    private final Set<ProductCharacteristicDetailsResponse> characteristics;

    private final List<ProductImageDetailsResponse> images;

    private final List<ProductOpinionDetailsResponse> opinions;

    private final List<ProductQuestionDetailsResponse> questions;

    ProductDetailsResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.createdAt = product.getCreatedAt();
        this.category = new CategoryDetailsResponse(product.getCategory());
        this.characteristics = product.mapCharacteristics(ProductCharacteristicDetailsResponse::new);
        this.images = product.mapImages(ProductImageDetailsResponse::new);
        this.opinions = product.mapOpinions(ProductOpinionDetailsResponse::new);
        this.questions = product.mapQuestions(ProductQuestionDetailsResponse::new);
        this.averageRate = product.mapOpinions(ProductOpinion::getRate)
                .stream()
                .mapToInt(rate -> rate)
                .average()
                .orElse(0.0D);
        this.rateAmount = product.mapOpinions(e -> e).size();
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

    public Double getAverageRate() {
        return averageRate;
    }

    public Integer getRateAmount() {
        return rateAmount;
    }

    public CategoryDetailsResponse getCategory() {
        return category;
    }

    public Set<ProductCharacteristicDetailsResponse> getCharacteristics() {
        return characteristics;
    }

    public List<ProductImageDetailsResponse> getImages() {
        return images;
    }

    public List<ProductOpinionDetailsResponse> getOpinions() {
        return opinions;
    }

    public List<ProductQuestionDetailsResponse> getQuestions() {
        return questions;
    }
}

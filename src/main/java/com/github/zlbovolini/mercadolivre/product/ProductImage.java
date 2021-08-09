package com.github.zlbovolini.mercadolivre.product;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @URL
    private String uri;

    @NotBlank
    private String type;

    @NotNull
    @Positive
    private Long size;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Deprecated
    ProductImage() {}

    ProductImage(String name, URI uri, String type, Long size, Product product) {
        this.name = name;
        this.uri = uri.toString();
        this.type = type;
        this.size = size;
        this.product = product;
    }

    ProductImage(@NotNull Resource resource, @NotNull Product product) {
        this.name = resource.getName();
        this.uri = resource.getUri().toString();
        this.type = resource.getType();
        this.size = resource.getSize();
        this.product = product;
    }

    public String getUri() {
        return uri;
    }
}

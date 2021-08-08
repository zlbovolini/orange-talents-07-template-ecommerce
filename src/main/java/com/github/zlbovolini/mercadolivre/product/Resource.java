package com.github.zlbovolini.mercadolivre.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;

class Resource {

    @NotBlank
    private final String name;

    @NotNull
    private final URI uri;

    @NotBlank
    private final String type;

    @NotNull
    @Positive
    private final Long size;

    Resource(String name, URI uri, String type, Long size) {
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.size = size;
    }

    String getName() {
        return name;
    }

    URI getUri() {
        return uri;
    }

    String getType() {
        return type;
    }

    Long getSize() {
        return size;
    }
}
